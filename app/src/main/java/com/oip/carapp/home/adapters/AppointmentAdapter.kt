package com.oip.carapp.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.Appointment
import kotlinx.android.synthetic.main.appointment_item.view.*

class AppointmentAdapter(private val list: ArrayList<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>() {

    inner class AppointmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AppointmentHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        val appointment = list[position]

        holder.itemView.apply {
            username.text = appointment.username
            amount.text = appointment.amount
            date.text = appointment.date
            serviceText.text = appointment.service
            providerText.text = appointment.provider
            timeText.text = appointment.time
        }
    }
}