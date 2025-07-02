package com.gigaprod.gigafilm.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.MovieAdapter
import com.gigaprod.gigafilm.model.Movie
import com.gigaprod.gigafilm.ui.custom.CardStackLayoutManager

class MoviesFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

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
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val movie = adapter.currentList()[position]

                if (direction == ItemTouchHelper.LEFT) {
                    println("Disliked: ${movie.title}")
                } else if (direction == ItemTouchHelper.RIGHT) {
                    println("Liked: ${movie.title}")
                }

                adapter.removeAt(position)
            }

        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun sampleMovies(): List<Movie> {
        return listOf(
            Movie(
                "zzzzzz",
                "zzzzzzzzzzzzzzzzzzz",
                "https://avatars.mds.yandex.net/get-kinopoisk-image/1600647/430042eb-ee69-4818-aed0-a312400a26bf/300x450"
            ),
            Movie(
                "zzzzzz1",
                "zzzzzzzzzzzzzzzzzzz2",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/AdIhqttutOdkKUttw8ofld870Dx.jpg"
            ),
            Movie(
                "zzzzzz3",
                "zzzzzzzzzzzzzzzzzzz3",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/8uOIWsrHvBTeZP4LSf25NomvLb6.jpg"
            ),
            Movie(
                "zzzzzz4",
                "zzzzzzzzzzzzzzzzzzz4",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/aOpByJVRjjKncEYIOQZD3CcpYdE.jpg"
            ),
            Movie(
                "zzzzzz5",
                "zzzzzzzzzzzzzzzzzzz5",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/lXjjq925EMCWEBh9iISMFLKrBtg.jpg"
            ),
            Movie(
                "Человек-паук: Через вселенные",
                "2018, фильм",
                "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/wmEKJr81CABBU68Qy2wYPwQHn0L.jpg"
            )
        )
    }
}