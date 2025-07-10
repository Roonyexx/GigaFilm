package com.gigaprod.gigafilm.ui.main

import Content
import Movie
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.MovieAdapter
import com.gigaprod.gigafilm.ui.custom.CardStackLayoutManager
import com.gigaprod.gigafilm.ui.dialog.MovieInfoBottomSheet

class MoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private var infoShown = false

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

        adapter = MovieAdapter(sampleMovies().toMutableList())
        recyclerView.adapter = adapter


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
                    ItemTouchHelper.LEFT -> {
                        adapter.removeAt(position)
                    }
                    ItemTouchHelper.RIGHT -> {
                        adapter.removeAt(position)
                    }
                    ItemTouchHelper.UP -> {
                        MovieInfoBottomSheet(movie).show(parentFragmentManager, "movie_info")

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
                    super.onChildDraw(c, recyclerView, viewHolder, dX, clampedDY, actionState, isCurrentlyActive)
                }
            }
        })


        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun sampleMovies(): List<Content> {
        return listOf(
            Movie(
                id = 1,
                title = "Inception",
                original_title = "Inception",
                overview = "A mind-bending thriller where dreams can be controlled.",
                poster_path = "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/430042eb-ee69-4818-aed0-a312400a26bf/300x450",
                vote_average = 8.8f,
                vote_count = 20000,
                actors = listOf(),
                genres = listOf("Action", "Sci-Fi", "Thriller"),
                status_id = 1,
                user_score = 9,
                release_date = "2010-07-16",
                budget = 160000000,
                revenue = 1234,
                runtime = 148
            ),
            Movie(
                id = 1,
                title = "Inception",
                original_title = "Inception",
                overview = "A mind-bending thriller where dreams can be controlled.",
                poster_path = "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/430042eb-ee69-4818-aed0-a312400a26bf/300x450",
                vote_average = 8.8f,
                vote_count = 20000,
                actors = listOf(),
                genres = listOf("Action", "Sci-Fi", "Thriller"),
                status_id = 1,
                user_score = 9,
                release_date = "2010-07-16",
                budget = 160000000,
                revenue = 12312,
                runtime = 148
            ),
            Movie(
                id = 1,
                title = "Inception",
                original_title = "Inception",
                overview = "A mind-bending thriller where dreams can be controlled.",
                poster_path = "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/430042eb-ee69-4818-aed0-a312400a26bf/300x450",
                vote_average = 8.8f,
                vote_count = 20000,
                actors = listOf(),
                genres = listOf("Action", "Sci-Fi", "Thriller"),
                status_id = 1,
                user_score = 9,
                release_date = "2010-07-16",
                budget = 160000000,
                revenue = 12321,
                runtime = 148
            ),
            Movie(
                id = 1,
                title = "Inception",
                original_title = "Inception",
                overview = "A mind-bending thriller where dreams can be controlled.",
                poster_path = "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/430042eb-ee69-4818-aed0-a312400a26bf/300x450",
                vote_average = 8.8f,
                vote_count = 20000,
                actors = listOf(),
                genres = listOf("Action", "Sci-Fi", "Thriller"),
                status_id = 1,
                user_score = 9,
                release_date = "2010-07-16",
                budget = 160000000,
                revenue = 12321,
                runtime = 148
            )
        )
    }
}