package com.gigaprod.gigafilm.ui.dialog

import Content
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.ActorCardAdapter
import com.gigaprod.gigafilm.adapter.ContentSourceAdapter
import com.gigaprod.gigafilm.api.ContentBase
import com.gigaprod.gigafilm.api.ContentScore
import com.gigaprod.gigafilm.api.ContentSource
import com.gigaprod.gigafilm.api.ContentStatus
import com.gigaprod.gigafilm.network.ServerRepository
import com.gigaprod.gigafilm.ui.custom.SharedViewModel
import com.gigaprod.gigafilm.ui.custom.getVoteColor
import com.gigaprod.gigafilm.ui.main.MainActivity
import com.gigaprod.gigafilm.ui.main.Status
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.color.MaterialColors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.getValue

class MovieInfoBottomSheet(private val movie: Content) : BottomSheetDialogFragment() {

    private lateinit var actorsRecyclerView: RecyclerView
    private lateinit var sourcesRecyclerView: RecyclerView
    private lateinit var serverRepository: ServerRepository
    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.dialog_movie_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.titleTextView).text = movie.getDisplayName()
        view.findViewById<TextView>(R.id.baseInfoTextView).text = movie.getBaseInfo()
        view.findViewById<TextView>(R.id.genresTextView).text = movie.getGenresList()
        view.findViewById<TextView>(R.id.overviewTextView).text = movie.overview
        val activity = requireActivity() as MainActivity
        serverRepository = activity.serverRepository

        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView)
        sourcesRecyclerView = view.findViewById(R.id.sourcesRecyclerView)

        actorsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        sourcesRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        val posterImageView = view.findViewById<ImageView>(R.id.posterImageView)
        val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movie.poster_path
        Glide.with(this).load(url).into(posterImageView)

        ratingViewSetup(view)

        val actorAdapter: ActorCardAdapter = ActorCardAdapter(movie.actors?.toMutableList() ?: mutableListOf())
        actorsRecyclerView.adapter = actorAdapter
        if(actorAdapter.itemCount == 0) view.findViewById<LinearLayout>(R.id.actorsLayout).visibility = View.GONE

        lifecycleScope.launch {
            val sourcedContent: ContentBase = ContentBase(movie.id, movie.contentType)
            val sources: List<ContentSource>? = serverRepository.getWatchSource(sourcedContent)

            val sourcesAdapter: ContentSourceAdapter = ContentSourceAdapter(sources?.toMutableList() ?: mutableListOf())
            sourcesRecyclerView.adapter = sourcesAdapter
            if(sourcesAdapter.itemCount != 0) view.findViewById<LinearLayout>(R.id.sourcesLayout).visibility = View.VISIBLE
        }
    }



    override fun getTheme() = R.style.AppBottomSheetDialogTheme

    override fun onStart() {
        super.onStart()
        dialog?.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog?.let { dlg ->
            val bottomSheet = dlg.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.layoutParams?.height = (resources.displayMetrics.heightPixels * 0.8).toInt()
            val behavior = BottomSheetBehavior.from(bottomSheet!!)
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }


    fun ratingViewSetup(view: View) {
        val ratingViews = listOf(
            R.id.rating1, R.id.rating2, R.id.rating3, R.id.rating4, R.id.rating5,
            R.id.rating6, R.id.rating7, R.id.rating8, R.id.rating9, R.id.rating10
        ).map { view.findViewById<TextView>(it) }


        movie.user_score?.let { score ->
            ratingViews.getOrNull(score - 1)?.setTextColor(
                ContextCompat.getColor(requireContext(), getVoteColor(score.toFloat()))
            )
        }

        val defaultColor = MaterialColors.getColor(view, com.google.android.material.R.attr.colorOnSurface)

        ratingViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {
                val selectedRating = index + 1
                val selectedStatus = when (selectedRating) {
                    in 1..5 -> Status.dislike.status
                    in 6..10 -> Status.like.status
                    else -> Status.unrated.status
                }
                val oldScore = movie.user_score
                val oldStatus = movie.status_id

                movie.user_score = selectedRating
                movie.status_id = selectedStatus


                if(oldScore == null && (oldStatus == null || oldStatus == Status.unrated.status)) {
                    sharedViewModel.setContent(movie)
                    sharedViewModel.setBarContent(movie)
                }
                else {
                    sharedViewModel.setBarContent(movie)
                }

                ratingViews.forEach { it.setTextColor(defaultColor) }
                val colorRes = getVoteColor(selectedRating.toFloat())
                val selectedColor = ContextCompat.getColor(requireContext(), colorRes)
                textView.setTextColor(selectedColor)

                GlobalScope.launch {
                    val scoreRequest: ContentScore = ContentScore(movie.id, movie.contentType, selectedRating)
                    val statusRequest: ContentStatus = ContentStatus(movie.id, movie.contentType, selectedStatus)

                    serverRepository.setUserScore(scoreRequest)
                    serverRepository.setContentStatus(statusRequest)
                }
            }
        }
    }
}
