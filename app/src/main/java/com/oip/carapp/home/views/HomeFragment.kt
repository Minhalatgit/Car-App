package com.oip.carapp.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentHomeBinding
import com.oip.carapp.home.adapters.UpcomingAppointmentAdapter
import com.oip.carapp.home.adapters.DiscountAdapter
import com.oip.carapp.home.adapters.ServiceAdapter
import com.oip.carapp.home.models.UpcomingAppointment
import com.oip.carapp.home.models.OfferResponse
import com.oip.carapp.home.models.Service

class HomeFragment : BaseFragment(), ServiceAdapter.ServiceListener,
    UpcomingAppointmentAdapter.AppointmentListener, DiscountAdapter.DiscountListener {

    private val TAG = "HomeFragment"

    private lateinit var binding: FragmentHomeBinding

    private val serviceList = ArrayList<Service>()
    private val appointmentList = ArrayList<UpcomingAppointment>()
    private val discountList = ArrayList<OfferResponse>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serviceList.add(
            Service(
                "$ 25.00",
                "22 km",
                "Jump",
                "Lorem ipsum dolor"
            )
        )
        serviceList.add(
            Service(
                "$ 25.00",
                "22 km",
                "Tow",
                "Lorem ipsum dolor"
            )
        )
        serviceList.add(
            Service(
                "$ 25.00",
                "22 km",
                "Lockout",
                "Lorem ipsum dolor"
            )
        )

        appointmentList.add(
            UpcomingAppointment(
                "image url",
                "Engine replacement",
                "12/11/2021",
                "Tomorrow"
            )
        )
        appointmentList.add(
            UpcomingAppointment(
                "image url",
                "Engine replacement",
                "12/11/2021",
                "Tomorrow"
            )
        )
        appointmentList.add(
            UpcomingAppointment(
                "image url",
                "Engine replacement",
                "12/11/2021",
                "Tomorrow"
            )
        )
        appointmentList.add(
            UpcomingAppointment(
                "image url",
                "Engine replacement",
                "12/11/2021",
                "Tomorrow"
            )
        )
        appointmentList.add(
            UpcomingAppointment(
                "image url",
                "Engine replacement",
                "12/11/2021",
                "Tomorrow"
            )
        )
        discountList.add(OfferResponse("1", "Engine Analysis", "20%","image url"))
        discountList.add(OfferResponse("2", "Engine Analysis", "20%","image url"))
        discountList.add(OfferResponse("3", "Engine Analysis", "20%","image url"))
        discountList.add(OfferResponse("4", "Engine Analysis", "20%","image url"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)

        // service list
        binding.serviceList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.serviceList.adapter = ServiceAdapter(serviceList, requireContext(), this)

        // appointment list
        binding.appointmentList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.appointmentList.adapter =
            UpcomingAppointmentAdapter(appointmentList, requireContext(), this)

        binding.discountList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        binding.discountList.adapter =
            DiscountAdapter(discountList, requireContext(), this)
        return binding.root
    }

    override fun onServiceClick(position: Int) {
        Log.d(TAG, "Service position $position")
    }

    override fun onAppointmentClick(position: Int) {
        Log.d(TAG, "Appointment position $position")
    }

    override fun onDiscountClick(position: Int) {
        Log.d(TAG, "Discount position $position")
    }
}