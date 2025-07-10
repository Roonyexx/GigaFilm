package com.gigaprod.gigafilm.adapter

import Content
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.gigaprod.gigafilm.R
import com.bumptech.glide.*

class MovieAdapter(
    private val movies: MutableList<Content>
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>()
{
    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val title: TextView = view.findViewById(R.id.filmTitleText)
        val description: TextView = view.findViewById(R.id.filmDescriptionText)
        val image: ImageView = view.findViewById(R.id.posterFilmImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_card, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.title.text = movie.getDisplayName()
        holder.description.text = movie.overview
        Glide.with(holder.itemView).load(movie.poster_path).override(800,1200).into(holder.image)
    }

    fun removeAt(position: Int) {
        val movie : Content = movies[position]
        movies.removeAt(position)
        notifyItemRemoved(position)
        movies.add(movie)
    }

    fun addMovie(movie: Content) {
        movies.add(movie)
        notifyItemInserted(movies.size)
    }

    fun addMovieList(movieList: MutableList<Content>) {
        for (movie in movieList) {
            addMovie(movie)
        }
    }

    fun currentList(): List<Content> = movies
}