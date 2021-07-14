package com.oip.carapp.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.AppointmentResponse
import com.oip.carapp.home.models.UpcomingAppointment
import com.oip.carapp.utils.Constants.BASE_URL_IMAGES
import com.oip.carapp.utils.SERVER_DATE_FORMAT
import com.oip.carapp.utils.UPCOMING_APPOINTMENT_TIME_FORMAT
import com.oip.carapp.utils.getDate
import com.squareup.picasso.Picasso

class UpcomingAppointmentAdapter(
    private val list: List<AppointmentResponse>,
    val context: Context,
    private val listener: AppointmentListener
) :
    RecyclerView.Adapter<UpcomingAppointmentAdapter.UpcomingAppointmentViewHolder>() {

    inner class UpcomingAppointmentViewHolder(itemView: View, listener: AppointmentListener) :
        RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.appointmentName)
        val date: TextView = itemView.findViewById(R.id.appointmentDate)
        val day: TextView = itemView.findViewById(R.id.day)
        val serviceImage: ImageView = itemView.findViewById(R.id.serviceImage)

        init {
            itemView.setOnClickListener {
                listener.onAppointmentClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        UpcomingAppointmentViewHolder(
            LayoutInflater.from(context).inflate(R.layout.upcoming_appointment_item, parent, false),
            listener
        )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UpcomingAppointmentViewHolder, position: Int) {
        val appointment = list[position]

        holder.apply {
            Picasso.get().load(BASE_URL_IMAGES + appointment.serviceImage).into(serviceImage)
            name.text = appointment.title
            date.text = getDate(
                appointment.createdAt, SERVER_DATE_FORMAT,
                UPCOMING_APPOINTMENT_TIME_FORMAT
            )
        }

    }

    interface AppointmentListener {
        fun onAppointmentClick(position: Int)
    }
}