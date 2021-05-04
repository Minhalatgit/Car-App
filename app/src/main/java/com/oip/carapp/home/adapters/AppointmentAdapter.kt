package com.oip.carapp.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.Appointment

class AppointmentAdapter(
    private val list: ArrayList<Appointment>,
    val context: Context,
    private val listener: AppointmentListener
) :
    RecyclerView.Adapter<AppointmentAdapter.ServiceViewHolder>() {

    inner class ServiceViewHolder(itemView: View, listener: AppointmentListener) :
        RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.appointmentName)
        val date: TextView = itemView.findViewById(R.id.appointmentDate)
        val day: TextView = itemView.findViewById(R.id.day)

        init {
            itemView.setOnClickListener {
                listener.onAppointmentClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ServiceViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_appointment, parent, false), listener
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ServiceViewHolder, position: Int) {
        val appointment = list[position]

        holder.apply {
            name.text = appointment.title
            date.text = appointment.date
            day.text = appointment.day
        }

    }

    interface AppointmentListener {
        fun onAppointmentClick(position: Int)
    }
}