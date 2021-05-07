package com.oip.carapp.home.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentFluidBinding
import com.oip.carapp.home.adapters.CarServiceAdapter
import com.oip.carapp.home.models.CarService

class FluidFragment : Fragment(), CarServiceAdapter.ServiceListener {

    private lateinit var binding: FragmentFluidBinding

    private lateinit var fluidServiceList: RecyclerView

    private val serviceList = ArrayList<CarService>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "image url"
            )
        )
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "image url"
            )
        )
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "image url"
            )
        )
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "image url"
            )
        )
        serviceList.add(
            CarService(
                "Car Diagnostic",
                "Moin Motor Workshop",
                "2,5 Km",
                true,
                "image url"
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFluidBinding.inflate(layoutInflater, container, false)

        fluidServiceList = binding.fluidServiceList
        fluidServiceList.layoutManager = LinearLayoutManager(requireActivity())
        fluidServiceList.adapter = CarServiceAdapter(serviceList, requireContext(), this)

        return binding.root
    }

    override fun onServiceClick(position: Int) {

    }
}