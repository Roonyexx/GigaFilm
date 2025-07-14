package com.gigaprod.gigafilm.ui.main

import Content
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.MovieAdapter
import com.gigaprod.gigafilm.api.StandartResponse
import com.gigaprod.gigafilm.api.ContentStatus
import com.gigaprod.gigafilm.network.ServerRepository
import com.gigaprod.gigafilm.ui.custom.CardStackLayoutManager
import com.gigaprod.gigafilm.ui.custom.SharedViewModel
import com.gigaprod.gigafilm.ui.dialog.MovieInfoBottomSheet
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

enum class Status(val status: Int) {
    like(1),
    dislike(2),
    unrated(3)
}
class MoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private var infoShown = false
    private var RecommedationsJob: Job? = null
    private lateinit var serverRepository: ServerRepository

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = CardStackLayoutManager()

        val activity = requireActivity() as MainActivity
        serverRepository = activity.serverRepository

        lifecycleScope.launch {
            adapter = MovieAdapter(serverRepository.getRecommendations().toMutableList())
            recyclerView.adapter = adapter
        }



        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT or ItemTouchHelper.UP
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = adapter.currentList()[position]

                when (direction) {
                    ItemTouchHelper.LEFT, ItemTouchHelper.RIGHT -> {
                        adapter.removeAt(position)

                        if (adapter.itemCount < 5 && (RecommedationsJob == null || RecommedationsJob?.isActive == false)) {
                            RecommedationsJob = lifecycleScope.launch {
                                val content: List<Content> =
                                    serverRepository.getRecommendations()
                                adapter.addMovieList(content.toMutableList())
                            }
                        }
                        lifecycleScope.launch {
                            if (direction == ItemTouchHelper.LEFT) movie.status_id = Status.dislike.status
                            else movie.status_id = Status.like.status

                            sendData(movie)

                            val request: ContentStatus =
                                ContentStatus(movie.id, movie.contentType, movie.status_id!!)
                            val response: StandartResponse =
                                serverRepository.setContentStatus(request)
                        }

                    }

                    ItemTouchHelper.UP -> {
                        val existing = parentFragmentManager.findFragmentByTag("movie_info")
                        if (existing == null) {
                            MovieInfoBottomSheet(movie).show(parentFragmentManager, "movie_info")
                        }

                        viewHolder.itemView.animate()
                            .translationX(0f)
                            .translationY(0f)
                            .setDuration(200)
                            .start()
                        adapter.notifyItemChanged(position)
                    }

                }
            }


            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                val maxUpDistance = -100f

                val clampedDY = if (dY < maxUpDistance) maxUpDistance else dY

                if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                    viewHolder.itemView.translationX = dX
                    viewHolder.itemView.translationY = clampedDY
                } else {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        clampedDY,
                        actionState,
                        isCurrentlyActive
                    )
                }
            }
        })


        itemTouchHelper.attachToRecyclerView(recyclerView)

        sharedViewModel.barContent.observe(viewLifecycleOwner) { content ->
            val index = adapter.findContentById(content.id)
            if (index != null) {
                adapter.removeAt(index)
            }
        }
    }
    private fun sendData(content: Content) {

        sharedViewModel.setContent(content)
    }
}
