package com.oip.carapp.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.History
import kotlinx.android.synthetic.main.history_item.view.*

class HistoryAdapter(private val list: ArrayList<History>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryHolder>() {

    inner class HistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = HistoryHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: HistoryHolder, position: Int) {
        val history = list[position]

        holder.itemView.apply {
            profileImage.setImageResource(history.profileImage)
            username.text = history.username
            amount.text = history.amount
            distance.text = history.distance
            pickupAddress.text = history.pickupAddress
            dropOffAddress.text = history.dropOffAddress
        }
    }
}