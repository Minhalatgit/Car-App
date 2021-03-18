package com.oip.carapp.home.views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentMapBinding
import com.oip.carapp.home.adapters.ServiceAdapter
import com.oip.carapp.home.models.Service
import com.suke.widget.SwitchButton

class MapFragment : Fragment(), ServiceAdapter.ServiceListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var servicesRecyclerView: RecyclerView
    private val list = ArrayList<Service>()

    lateinit var toolbar: Toolbar
    lateinit var title: TextView
    lateinit var switch: SwitchButton
    lateinit var navigationIcon: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentMapBinding.inflate(inflater, container, false)
        initViews()

        servicesRecyclerView.adapter =
            ServiceAdapter(
                list,
                requireContext(),
                this
            )

        val supportFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportFragment.getMapAsync {
        }

        switch.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                binding.statusLayout.visibility = View.VISIBLE
            } else {
                binding.statusLayout.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onServiceClick(position: Int) {
        // Sending service name to next Service fragment
        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            .navigate(
                MapFragmentDirections.actionMapFragmentToServiceFragment(
                    list[position].serviceName
                )
            )
    }

    private fun initViews() {
        toolbar = activity?.findViewById(R.id.toolbar)!!
        title = toolbar.findViewById(R.id.title)
        switch = toolbar.findViewById(R.id.switch_button)
        navigationIcon = toolbar.findViewById(R.id.navigationIcon)

        navigationIcon.visibility = View.VISIBLE
        switch.visibility = View.VISIBLE
        title.text = "" // no title for map fragment
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)

        servicesRecyclerView = binding.serviceRecyclerView
        servicesRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }
}