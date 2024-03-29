package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceBinding

class ServiceFragment : BaseFragment() {

    private lateinit var binding: FragmentServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentServiceBinding.inflate(inflater, container, false)

        binding.mechanic.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(
                    ServiceFragmentDirections.actionServiceFragmentToServiceListFragment(
                        "Car Mechanic Service",
                        "1"
                    )
                )
        }
        binding.denting.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(
                    ServiceFragmentDirections.actionServiceFragmentToServiceListFragment(
                        "Car Denting Service",
                        "2"
                    )
                )
        }
        binding.electrician.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(
                    ServiceFragmentDirections.actionServiceFragmentToServiceListFragment(
                        "Car Electrician Service",
                        "3"
                    )
                )
        }

        return binding.root
    }
}