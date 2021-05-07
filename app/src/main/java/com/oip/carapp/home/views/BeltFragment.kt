package com.oip.carapp.home.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentBeltBinding
import com.oip.carapp.databinding.FragmentOilBinding
import com.oip.carapp.home.adapters.CarServiceAdapter
import com.oip.carapp.home.models.CarService

class BeltFragment : Fragment(), CarServiceAdapter.ServiceListener {

    private lateinit var binding: FragmentBeltBinding
    private lateinit var beltServiceList: RecyclerView

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
        binding = FragmentBeltBinding.inflate(layoutInflater, container, false)

        beltServiceList = binding.beltServiceList
        beltServiceList.layoutManager = LinearLayoutManager(requireActivity())
        beltServiceList.adapter = CarServiceAdapter(serviceList, requireContext(), this)

        return binding.root
    }

    override fun onServiceClick(position: Int) {

    }
}