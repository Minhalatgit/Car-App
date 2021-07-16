package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.BaseFragment
import com.oip.carapp.databinding.FragmentNotificationBinding
import com.oip.carapp.home.adapters.NotificationAdapter
import com.oip.carapp.home.viewmodel.NotificationViewModel
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar

class NotificationFragment : BaseFragment() {

    private lateinit var binding: FragmentNotificationBinding
    private lateinit var notificationRecyclerView: RecyclerView
    private lateinit var viewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNotificationBinding.inflate(inflater, container, false)

        notificationRecyclerView = binding.notificationRecyclerView
        notificationRecyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel = ViewModelProvider(this).get(NotificationViewModel::class.java)
        showProgressBar(window, binding.progress)
        viewModel.getNotifications()

        viewModel.notificationList.observe(viewLifecycleOwner, Observer {
            hideProgressBar(window, binding.progress)
            notificationRecyclerView.adapter = NotificationAdapter(it)
        })

        return binding.root
    }

}