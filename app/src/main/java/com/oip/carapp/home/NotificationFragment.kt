package com.oip.carapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentHistoryBinding
import com.oip.carapp.databinding.FragmentNotificationBinding
import com.oip.carapp.databinding.FragmentWalletBinding
import com.oip.carapp.home.adapters.NotificationAdapter
import com.oip.carapp.home.models.Notification

class NotificationFragment : Fragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var notificationRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        val list = ArrayList<Notification>()
        list.apply {
            add(Notification("System", "Booking #1234 has been success..."))
            add(Notification("Promotion", "Booking #1234 has been success..."))
            add(Notification("Promotion", "Booking #1234 has been success..."))
            add(Notification("System", "Booking #1234 has been success..."))
            add(Notification("Promotion", "Booking #1234 has been success..."))
        }

        notificationRecyclerView = binding.notificationRecyclerView
        notificationRecyclerView.layoutManager = LinearLayoutManager(activity)
        notificationRecyclerView.adapter = NotificationAdapter(list)

        return binding.root
    }
}