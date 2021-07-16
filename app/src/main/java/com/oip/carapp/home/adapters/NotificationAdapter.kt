package com.oip.carapp.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.NotificationResponse
import kotlinx.android.synthetic.main.notification_item.view.*

class NotificationAdapter(private val list: List<NotificationResponse>) :
    RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {

    inner class NotificationHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = NotificationHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        val notification = list[position]

        holder.itemView.apply {
            title.text = notification.title
            description.text = notification.content
        }
    }
}