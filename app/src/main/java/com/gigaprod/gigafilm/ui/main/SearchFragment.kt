package com.gigaprod.gigafilm.ui.main

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.MovieListAdapter
import com.gigaprod.gigafilm.api.ApiClient
import com.gigaprod.gigafilm.network.ServerRepository
import com.gigaprod.gigafilm.ui.dialog.MovieInfoBottomSheet
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.max

class SearchFragment : Fragment() {

    private lateinit var searchInput: EditText
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieListAdapter
    private lateinit var serverRepository: ServerRepository

    private var searchJob: Job? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchInput = view.findViewById(R.id.searchEditText)
        recyclerView = view.findViewById(R.id.searchRecyclerView)

        val activity = requireActivity() as MainActivity
        serverRepository = activity.serverRepository


        val spanCount = calculateSpanCount(requireContext(), 160)
        recyclerView.layoutManager = GridLayoutManager(requireContext(), spanCount)

        adapter = MovieListAdapter(mutableListOf()) {}
        recyclerView.adapter = adapter

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString().trim()

                searchJob?.cancel()
                searchJob = lifecycleScope.launch {
                    delay(300)
                    if (query.isNotEmpty()) {
                        performSearch(query)
                    } else {
                        adapter = MovieListAdapter(mutableListOf()) {}
                        recyclerView.adapter = adapter
                    }
                }
            }


            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun calculateSpanCount(context: Context, itemWidthDp: Int): Int {
        val displayMetrics = context.resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density
        return max(1, (screenWidthDp / itemWidthDp).toInt())
    }

    private fun performSearch(query: String) {
        lifecycleScope.launch {
            val movies = serverRepository.searchMovies(query)
            adapter = MovieListAdapter(mutableListOf()) { content ->
                val existing = parentFragmentManager.findFragmentByTag("movie_info")
                if(existing == null)
                    MovieInfoBottomSheet(content).show(parentFragmentManager, "movie_info")
            }
            recyclerView.adapter = adapter
            adapter.addMovieList(movies)
        }
    }
}
