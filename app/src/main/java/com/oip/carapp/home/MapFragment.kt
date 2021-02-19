package com.oip.carapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentMapBinding
import com.oip.carapp.home.adapters.ServiceAdapter
import com.oip.carapp.home.models.Service

class MapFragment : Fragment(), ServiceAdapter.ServiceListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var servicesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentMapBinding.inflate(inflater, container, false)
        servicesRecyclerView = binding.serviceRecyclerView
        val supportFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        servicesRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)

        val list = ArrayList<Service>()
        list.add(
            Service(
                "$ 25.00",
                "22 km",
                "Jump",
                "Lorem ipsum dolor"
            )
        )
        list.add(
            Service(
                "$ 25.00",
                "22 km",
                "Tow",
                "Lorem ipsum dolor"
            )
        )
        list.add(
            Service(
                "$ 25.00",
                "22 km",
                "Lockout",
                "Lorem ipsum dolor"
            )
        )

        servicesRecyclerView.adapter =
            ServiceAdapter(
                list,
                requireContext(),
                this
            )

        supportFragment.getMapAsync {

        }

        return binding.root
    }

    override fun onServiceClick(position: Int) {
        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            .navigate(R.id.action_mapFragment_to_serviceFragment);
    }

}