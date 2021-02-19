package com.oip.carapp.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.Car

class ChooseCarAdapter(
    private val list: ArrayList<Car>,
    val context: Context,
    private val listener: ChooseCarListener
) :
    RecyclerView.Adapter<ChooseCarAdapter.ChooseCarViewHolder>() {

    inner class ChooseCarViewHolder(itemView: View, listener: ChooseCarListener) :
        RecyclerView.ViewHolder(itemView) {
        val carImage: ImageView = itemView.findViewById(R.id.carImage)
        val title: TextView = itemView.findViewById(R.id.title)
        val rate: TextView = itemView.findViewById(R.id.rate)
        val info: TextView = itemView.findViewById(R.id.info)

        init {
            itemView.setOnClickListener {
                listener.onCarClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ChooseCarViewHolder(
        LayoutInflater.from(context).inflate(R.layout.choose_car_item, parent, false), listener
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ChooseCarViewHolder, position: Int) {
        val car = list[position]

        holder.apply {
            carImage.setImageResource(car.carImage)
            title.text = car.title
            rate.text = car.rate
            info.text = car.carInfo
        }
    }

    interface ChooseCarListener {
        fun onCarClick(position: Int)
    }
}