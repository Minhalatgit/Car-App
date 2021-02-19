package com.oip.carapp.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.Service

class ServiceAdapter(
    private val list: ArrayList<Service>,
    val context: Context,
    private val listener: ServiceListener
) :
    RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(itemView: View, listener: ServiceListener) :
        RecyclerView.ViewHolder(itemView) {
        val layout: RelativeLayout = itemView.findViewById(R.id.layout)
        val serialNumber: TextView = itemView.findViewById(R.id.serialNumber)
        val ratePrice: TextView = itemView.findViewById(R.id.ratePrice)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val serviceName: TextView = itemView.findViewById(R.id.serviceName)
        val serviceDescription: TextView = itemView.findViewById(R.id.serviceDescription)

        init {
            itemView.setOnClickListener {
                listener.onServiceClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ServiceViewHolder(
        LayoutInflater.from(context).inflate(R.layout.services_item, parent, false), listener
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val serviceItem = list[position]

        holder.apply {

            val gradientId = when (position) {
                0 -> R.drawable.yellow_gradient
                1 -> R.drawable.green_gradient
                else -> R.drawable.purple_gradient
            }

            layout.background = ContextCompat.getDrawable(context, gradientId);
            serialNumber.text = position.toString()
            ratePrice.text = serviceItem.ratePrice
            distance.text = serviceItem.distance
            serviceName.text = serviceItem.serviceName
            serviceDescription.text = serviceItem.serviceDescription
        }

    }

    public interface ServiceListener {
        fun onServiceClick(position: Int)
    }
}