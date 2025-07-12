package com.gigaprod.gigafilm.ui.main

import Content
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.MovieListAdapter
import com.gigaprod.gigafilm.api.ApiClient
import com.gigaprod.gigafilm.network.ServerRepository
import com.gigaprod.gigafilm.ui.custom.SharedViewModel
import kotlinx.coroutines.launch
import kotlin.math.max


class ProfileFragment : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieListAdapter
    private lateinit var serverRepository: ServerRepository

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = view.findViewById(R.id.recyclerMovies)

        val activity = requireActivity() as MainActivity
        serverRepository = activity.serverRepository

        val spanCount = calculateSpanCount(requireContext(), 160)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        movieAdapter = MovieListAdapter(mutableListOf())
        recyclerView.adapter = movieAdapter

        sharedViewModel.content.observe(viewLifecycleOwner) { content ->
            movieAdapter.addMovieAtStart(content)
        }

        lifecycleScope.launch {
            val profileMovies: List<Content> = serverRepository.getUserFilms()
            movieAdapter.addMovieList(profileMovies)
        }
    }

    private fun calculateSpanCount(context: Context, itemWidthDp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return max(1, (screenWidthDp / itemWidthDp).toInt())
    }
}