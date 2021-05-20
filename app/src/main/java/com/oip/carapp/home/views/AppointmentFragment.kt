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
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentAppointmentBinding
import com.oip.carapp.home.adapters.AppointmentAdapter
import com.oip.carapp.home.models.Appointment
import com.oip.carapp.home.viewmodel.AppointmentViewModel
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar
import kotlin.collections.ArrayList

class AppointmentFragment : BaseFragment() {

    private lateinit var binding: FragmentAppointmentBinding
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var viewModel: AppointmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAppointmentBinding.inflate(inflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)

        viewModel = ViewModelProvider(this).get(AppointmentViewModel::class.java)
        showProgressBar(window, binding.progress)
        viewModel.getAppointments()

        historyRecyclerView = binding.historyRecyclerView
        historyRecyclerView.layoutManager = LinearLayoutManager(activity)

        viewModel.appointmentList.observe(viewLifecycleOwner, Observer {
            hideProgressBar(window, binding.progress)
            historyRecyclerView.adapter = AppointmentAdapter(it)
        })

        return binding.root
    }

}