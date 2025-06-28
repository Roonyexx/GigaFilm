package com.gigaprod.gigafilm.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.adapter.MovieAdapter
import com.gigaprod.gigafilm.model.Movie

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = MovieAdapter(sampleMovies().toMutableList())
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

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
            Movie("Inception", "A mind-bending thriller."),
            Movie("Interstellar", "A journey through space and time."),
            Movie("The Dark Knight", "A gritty superhero drama.")
        )
    }
}