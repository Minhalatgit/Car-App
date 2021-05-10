package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentAppointmentBinding
import com.oip.carapp.home.adapters.AppointmentAdapter
import com.oip.carapp.home.models.Appointment
import kotlin.collections.ArrayList

class AppointmentFragment : BaseFragment() {

    private lateinit var binding: FragmentAppointmentBinding
    private lateinit var historyRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)

        historyRecyclerView = binding.historyRecyclerView

        val list = ArrayList<Appointment>()
        list.apply {
            add(
                Appointment(
                    "Image url",
                    "Test",
                    "25.00",
                    "05-06-2021",
                    "Tow",
                    "Moin Motor Workshop",
                    "03:00 PM"
                )
            )
            add(
                Appointment(
                    "Image url",
                    "Test",
                    "25.00",
                    "05-06-2021",
                    "Tow",
                    "Moin Motor Workshop",
                    "03:00 PM"
                )
            )
            add(
                Appointment(
                    "Image url",
                    "Test",
                    "25.00",
                    "05-06-2021",
                    "Tow",
                    "Moin Motor Workshop",
                    "03:00 PM"
                )
            )
            add(
                Appointment(
                    "Image url",
                    "Test",
                    "25.00",
                    "05-06-2021",
                    "Tow",
                    "Moin Motor Workshop",
                    "03:00 PM"
                )
            )
            add(
                Appointment(
                    "Image url",
                    "Test",
                    "25.00",
                    "05-06-2021",
                    "Tow",
                    "Moin Motor Workshop",
                    "03:00 PM"
                )
            )
            add(
                Appointment(
                    "Image url",
                    "Test",
                    "25.00",
                    "05-06-2021",
                    "Tow",
                    "Moin Motor Workshop",
                    "03:00 PM"
                )
            )
        }

        historyRecyclerView.layoutManager = LinearLayoutManager(activity)
        historyRecyclerView.adapter = AppointmentAdapter(list)

        return binding.root
    }

}