package com.fizus.events.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.FragmentNavigator
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fizus.events.R
import com.fizus.events.model.Event
import com.fizus.events.utility.Config
import kotlinx.android.synthetic.main.item_row_event.view.*

class EventsAdapter(private val events: List<Event>, private val listener: Listener) :
    RecyclerView.Adapter<EventsAdapter.EventsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_event, parent, false)
        return EventsViewHolder(view, listener)
    }

    override fun getItemCount(): Int {
        return events.size
    }

    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        holder.bind(events[position])
    }

    class EventsViewHolder(itemView: View, listener: Listener) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.setOnClickListener {
                listener.onClick(adapterPosition)
            }
        }

        fun bind(event: Event) {
            Glide.with(itemView.context).load(Config.BASE_URL_UPLOADS + event.thumbnail)
                .into(itemView.iv_thumbnail)
            itemView.tv_title.text = event.title
            itemView.tv_category.text = event.category
            itemView.tv_status.text = event.status
        }
    }

    interface Listener {
        fun onClick(position: Int)
    }

}