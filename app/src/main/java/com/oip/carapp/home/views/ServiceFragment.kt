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
        window.statusBarColor = requireActivity().getColor(R.color.white)

        binding.mechanic.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(ServiceFragmentDirections.actionServiceFragmentToServiceListFragment("Car Mechanic Service"))
        }
        binding.denting.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(ServiceFragmentDirections.actionServiceFragmentToServiceListFragment("Car Denting Service"))
        }
        binding.electrician.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(ServiceFragmentDirections.actionServiceFragmentToServiceListFragment("Car Electrician Service"))
        }

        return binding.root
    }
}