package com.gigaprod.gigafilm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.model.Movie
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import android.graphics.drawable.Drawable
import com.bumptech.glide.Glide


class MovieListAdapter(
    private val movies: MutableList<Movie>
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>()
{
    class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)
    {
        val title: TextView = view.findViewById(R.id.filmTitleText)
        val image: ImageView = view.findViewById(R.id.posterFilmImage)
        val userRating: TextView = view.findViewById(R.id.filmRatingText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_profile_card, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        val movie = movies[position]
        holder.title.text = movie.title
        holder.userRating.text = movie.userRating?.toString() ?: "-"
        val url    = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + movie.poster_path
        Glide.with(holder.itemView).load(url).override(160,240).into(holder.image)

    }

    fun removeAt(position: Int) {
        val movie : Movie = movies[position]
        movies.removeAt(position)
        notifyItemRemoved(position)
        movies.add(movie)
    }

    fun addMovie(movie: Movie) {
        movies.add(movie)
        notifyItemInserted(movies.size)
    }

    fun addMovieList(movieList: MutableList<Movie>) {
        for (movie in movieList) {
            addMovie(movie)
        }
    }

    fun currentList(): List<Movie> = movies
}