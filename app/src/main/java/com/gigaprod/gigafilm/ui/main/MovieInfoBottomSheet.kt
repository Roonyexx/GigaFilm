package com.gigaprod.gigafilm.ui.dialog

import Content
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.ActorCardAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.color.MaterialColors

class MovieInfoBottomSheet(private val movie: Content) : BottomSheetDialogFragment() {

    private lateinit var actorsRecyclerView: RecyclerView

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
        actorsRecyclerView = view.findViewById(R.id.actorsRecyclerView)
        actorsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


        val posterImageView = view.findViewById<ImageView>(R.id.posterImageView)
        val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movie.poster_path
        Glide.with(this).load(url).into(posterImageView)

        ratingViewSetup(view)

        val adapter: ActorCardAdapter = ActorCardAdapter(movie.actors?.toMutableList() ?: mutableListOf())
        actorsRecyclerView.adapter = adapter
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

        val defaultColor = MaterialColors.getColor(view, com.google.android.material.R.attr.colorOnSurface)

        ratingViews.forEachIndexed { index, textView ->
            textView.setOnClickListener {

                ratingViews.forEach { it.setTextColor(defaultColor) }


                val selectedRating = index + 1
                val colorRes = when {
                    selectedRating <= 4 -> R.color.rating_red
                    selectedRating <= 6 -> R.color.rating_gray
                    else -> R.color.rating_green
                }
                val selectedColor = ContextCompat.getColor(requireContext(), colorRes)


                textView.setTextColor(selectedColor)

                Toast.makeText(requireContext(), "Оценка: $selectedRating", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
