package com.gigaprod.gigafilm.adapter

import Content
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.gigaprod.gigafilm.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.gigaprod.gigafilm.ui.main.Status


class MovieListAdapter(
    private val movies: MutableList<Content>,
    private val onItemClick: (Content) -> Unit
) : RecyclerView.Adapter<MovieListAdapter.MovieViewHolder>()
{
    class MovieViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
    {
        val title: TextView = view.findViewById(R.id.filmTitleText)
        val image: ImageView = view.findViewById(R.id.posterFilmImage)
        val userRating: TextView = view.findViewById(R.id.filmRatingText)
        val stausIcon: ImageView = view.findViewById(R.id.statusImage)

        val voteAverage: TextView = view.findViewById(R.id.voteAverage)

        fun bind(content: Content, onItemClick: (Content) -> Unit) {
            title.text = content.getDisplayName()
            userRating.text = content.user_score?.toString() ?: "-"
            val url = "https://image.tmdb.org/t/p/w600_and_h900_bestv2" + content.poster_path
            Glide.with(itemView)
                .load(url)
                .override(160,240)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(image)
            val icon : Int? = when(content.status_id) {
                Status.like.status -> { R.drawable.ic_like }
                Status.dislike.status -> { R.drawable.ic_dislike }
                null -> null
                else -> null
            }
            if (icon != null) stausIcon.setImageResource(icon)

            if(content.vote_count!= 0) {
                val ratingValue = content.vote_average ?: 0f
                val backgroundRes = when {
                    ratingValue < 5f -> R.drawable.bg_rating_red
                    ratingValue < 7f -> R.drawable.bg_rating_neutral
                    else -> R.drawable.bg_rating_green
                }
                voteAverage.setBackgroundResource(backgroundRes)
                voteAverage.text = content.vote_average.toString()
            }
            view.setOnClickListener {
                onItemClick(content)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_movie_profile_card, parent, false)
        return MovieViewHolder(view)
    }


    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie, onItemClick)
        startUpAnimate(holder)
    }
    private fun startUpAnimate(holder: MovieViewHolder) {
        holder.itemView.alpha = 0f
        holder.itemView.animate()
            .alpha(1f)
            .setDuration(300)
            .start()
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

    fun addMovieAtStart(content: Content) {
        movies.add(0, content)
        notifyItemInserted(0)
    }

    fun addMovieList(movieList: List<Content>) {
        for (movie in movieList) {
            addMovie(movie)
        }
    }

    fun findContentById(id: Int): Int? {
        movies.forEachIndexed() { index, content  ->
            if(content.id == id) {
                return index
            }
        }
        return null
    }

    fun currentList(): List<Content> = movies
}