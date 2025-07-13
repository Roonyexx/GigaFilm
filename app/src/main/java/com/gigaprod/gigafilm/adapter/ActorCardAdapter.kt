package com.gigaprod.gigafilm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gigaprod.gigafilm.R

import com.gigaprod.gigafilm.model.Actor

class ActorCardAdapter(
    private val actors: MutableList<Actor>
): RecyclerView.Adapter<ActorCardAdapter.ActorViewHolder>() {

    class ActorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.actorNameText)
        val character: TextView = view.findViewById(R.id.characterText)
        val actorPhoto: ImageView = view.findViewById(R.id.actorImage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ActorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_actor_card, parent, false)
        return ActorViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ActorViewHolder,
        position: Int
    ) {
        val actor = actors[position]
        holder.name.text = actor.name
        holder.character.text = actor.character
        holder.actorPhoto.setImageResource(R.drawable.ic_actor_default)
    }

    override fun getItemCount(): Int = actors.size
}