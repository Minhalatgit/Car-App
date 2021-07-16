package com.oip.carapp.home.views

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentHomeBinding
import com.oip.carapp.home.adapters.UpcomingAppointmentAdapter
import com.oip.carapp.home.adapters.DiscountAdapter
import com.oip.carapp.home.adapters.ServiceAdapter
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.home.viewmodel.HomeViewModel
import com.oip.carapp.utils.PreferencesHandler
import java.util.ArrayList

class HomeFragment : BaseFragment(), ServiceAdapter.ServiceListener,
    UpcomingAppointmentAdapter.AppointmentListener, DiscountAdapter.DiscountListener {

    private val TAG = "HomeFragment"

    private lateinit var binding: FragmentHomeBinding

    private lateinit var viewModel: HomeViewModel

    private var serviceList = ArrayList<ServiceResponse>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        binding.discountList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.serviceList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.appointmentList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewModel.getHomeData()

        viewModel.homeResponse.observe(viewLifecycleOwner, Observer {
            if (it.offers.isNotEmpty()) {
                PreferencesHandler.setOffer(it.offers[0].offerDiscount)
                binding.discount.text = "FLAT ${it.offers[0].offerDiscount}% OFF"
            }

            serviceList.clear()
            serviceList.addAll(it.services)

            binding.serviceList.adapter = ServiceAdapter(it.services, requireContext(), this)
            binding.discountList.adapter = DiscountAdapter(it.offers, requireContext(), this)
            binding.appointmentList.adapter =
                UpcomingAppointmentAdapter(it.appointments, requireContext(), this)
        })

        binding.viewAll.setOnClickListener {
            activity.findViewById<BottomNavigationView>(R.id.bottomView).selectedItemId =
                R.id.serviceFragment
        }
        binding.viewAllAppointments.setOnClickListener {
            it.findNavController().navigate(R.id.action_homeFragment_to_appointmentFragment)
        }
        binding.quickAssistance.setOnClickListener {
            //Open dialer
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:+923312226066")
            startActivity(intent)
        }

        return binding.root
    }

    override fun onServiceClick(position: Int) {
        Log.d(TAG, "Service position $position")
        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            .navigate(
                HomeFragmentDirections.actionHomeFragmentToServiceBookingFragment(
                    serviceList[position]
                )
            )
    }

    override fun onAppointmentClick(position: Int) {
        Log.d(TAG, "Appointment position $position")
    }

    override fun onDiscountClick(position: Int) {
        Log.d(TAG, "Discount position $position")
    }
}