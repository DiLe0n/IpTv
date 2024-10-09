package com.hanzeel.iptvandroidstudio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerAdapter(private val channelList: List<Channel>, private val onChannelClick: (Channel) -> Unit) : RecyclerView.Adapter<RecyclerAdapter.ChannelViewHolder>() {

    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.textViewName)
        val categoryTextView: TextView = view.findViewById(R.id.textViewCategory)
        val channelImageView: ImageView = view.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.channel_item, parent, false)
        return ChannelViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channelList[position]
        holder.nameTextView.text = channel.name
        holder.categoryTextView.text = channel.category

        Glide.with(holder.itemView.context)
            .load(channel.image)
            .into(holder.channelImageView)

        // Manejar el clic en un canal
        holder.itemView.setOnClickListener {
            onChannelClick(channel)
        }
    }

    override fun getItemCount(): Int {
        return channelList.size
    }
}

