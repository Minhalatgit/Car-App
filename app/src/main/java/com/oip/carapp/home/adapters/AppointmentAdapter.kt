package com.oip.carapp.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.AppointmentResponse
import com.oip.carapp.utils.*
import com.oip.carapp.utils.Constants.BASE_URL_IMAGES
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.appointment_item.view.*

class AppointmentAdapter(private val list: List<AppointmentResponse>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder>() {

    inner class AppointmentHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AppointmentHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.appointment_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AppointmentHolder, position: Int) {
        val appointment = list[position]

        holder.itemView.apply {
            Picasso.get().load(BASE_URL_IMAGES + appointment.serviceImage).into(storeImage)
            username.text = appointment.title
            amount.text = "$${appointment.amount}"
            date.text = getDate(
                appointment.appointmentDate, SERVER_DATE_FORMAT,
                APPOINTMENT_DATE_FORMAT
            )
            serviceText.text = appointment.serviceTitle
            providerText.text = appointment.storeTitle
            timeText.text = getDate(
                appointment.appointmentDate, SERVER_DATE_FORMAT,
                APPOINTMENT_TIME_FORMAT
            )
        }
    }
}