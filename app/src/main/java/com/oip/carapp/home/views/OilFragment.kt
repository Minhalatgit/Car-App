package com.oip.carapp.home.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentOilBinding
import com.oip.carapp.home.adapters.CarServiceAdapter
import com.oip.carapp.home.models.CarService

class OilFragment : Fragment(), CarServiceAdapter.ServiceListener {

    private lateinit var binding: FragmentOilBinding
    private lateinit var oilServiceList: RecyclerView

    private val serviceList = ArrayList<CarService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "Image url"
            )
        )
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "Image url"
            )
        )
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "Image url"
            )
        )
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "Image url"
            )
        )
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "Image url"
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOilBinding.inflate(inflater, container, false)

        oilServiceList = binding.oilServiceList
        oilServiceList.layoutManager = LinearLayoutManager(requireActivity())
        oilServiceList.adapter = CarServiceAdapter(serviceList, requireContext(), this)

        return binding.root
    }

    override fun onServiceClick(position: Int) {
        view?.findNavController()
            ?.navigate(R.id.action_serviceListFragment_to_serviceBookingFragment)
    }
}