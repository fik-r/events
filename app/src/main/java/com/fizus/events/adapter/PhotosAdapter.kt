package com.fizus.events.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fizus.events.R
import com.fizus.events.utility.Config
import kotlinx.android.synthetic.main.item_row_photos.view.*

class PhotosAdapter(private val photos: Array<String>, private val listener: Listener) :
    RecyclerView.Adapter<PhotosAdapter.PhotosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_photos, parent, false)
        return PhotosViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    override fun onBindViewHolder(holder: PhotosViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    class PhotosViewHolder(itemView: View, listener: Listener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }

        fun bind(photo: String) {
            Glide.with(itemView.context).load(Config.BASE_URL_UPLOADS + photo)
                .into(itemView.iv_photo)
        }
    }

    interface Listener {
        fun onClick(position: Int)
    }

}