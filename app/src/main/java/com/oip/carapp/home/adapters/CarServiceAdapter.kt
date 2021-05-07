package com.oip.carapp.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.CarService

class CarServiceAdapter(
    private val list: ArrayList<CarService>,
    val context: Context,
    private val listener: ServiceListener
) :
    RecyclerView.Adapter<CarServiceAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(itemView: View, listener: ServiceListener) :
        RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val title: TextView = itemView.findViewById(R.id.title)
        val address: TextView = itemView.findViewById(R.id.address)
        val distance: TextView = itemView.findViewById(R.id.distance)
        val favourite: ImageView = itemView.findViewById(R.id.favourite)

        init {
            itemView.setOnClickListener {
                listener.onServiceClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ServiceViewHolder(
        LayoutInflater.from(context).inflate(R.layout.car_service_item, parent, false), listener
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val service = list[position]

        holder.apply {
//            carImage.setImageResource(car.carImage)
            title.text = service.title
            address.text = service.address
            distance.text = service.distance
        }
    }

    interface ServiceListener {
        fun onServiceClick(position: Int)
    }
}