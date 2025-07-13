package com.gigaprod.gigafilm.adapter

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.gigaprod.gigafilm.R
import com.gigaprod.gigafilm.api.ContentSource
import androidx.core.net.toUri

class ContentSourceAdapter(
    val sources: MutableList<ContentSource>
) : RecyclerView.Adapter<ContentSourceAdapter.ContentSourceViewHolder>() {

    class ContentSourceViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val sourceImage: ImageView = view.findViewById(R.id.sourceImage)

        fun bind(source: ContentSource) {
            val url = source.img_src
            Glide.with(itemView)
                .load(url)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(sourceImage)
            view.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, source.link.toUri())
                view.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContentSourceViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_content_source, parent, false)
        return ContentSourceViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ContentSourceViewHolder,
        position: Int
    ) {
        val source = sources[position]
        holder.bind(source)
    }

    override fun getItemCount(): Int = sources.size
}