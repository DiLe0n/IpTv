package com.hanzeel.iptvandroidstudio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class RecyclerAdapter(private val channelList: List<Channel>) : RecyclerView.Adapter<RecyclerAdapter.ChannelViewHolder>() {

    // ViewHolder para el RecyclerView
    class ChannelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.textViewName)
        val categoryTextView: TextView = view.findViewById(R.id.textViewCategory)
        val channelImageView: ImageView = view.findViewById(R.id.imageView)
    }

    // Infla el layout de cada ítem
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.channel_item, parent, false)
        return ChannelViewHolder(view)
    }

    // Enlaza los datos con las vistas
    override fun onBindViewHolder(holder: ChannelViewHolder, position: Int) {
        val channel = channelList[position]
        holder.nameTextView.text = channel.name
        holder.categoryTextView.text = channel.category

        // Cargar la imagen usando Glide desde el link
        Glide.with(holder.itemView.context)
            .load(channel.image)
            .into(holder.channelImageView)
    }

    // Retorna el tamaño de la lista
    override fun getItemCount(): Int {
        return channelList.size
    }
}
