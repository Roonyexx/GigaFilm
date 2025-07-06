package com.gigaprod.gigafilm.ui.main

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.MovieListAdapter
import com.gigaprod.gigafilm.model.Movie
import kotlin.math.max


class ProfileFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerMovies)

        movieAdapter = MovieListAdapter(sampleMovies().toMutableList())

        val spanCount = calculateSpanCount(requireContext(), 160)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)
        recyclerView.adapter = movieAdapter

    }

    private fun calculateSpanCount(context: Context, itemWidthDp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return max(1, (screenWidthDp / itemWidthDp).toInt())
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