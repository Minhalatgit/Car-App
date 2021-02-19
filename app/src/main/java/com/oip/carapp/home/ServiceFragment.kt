package com.oip.carapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceBinding

class ServiceFragment : Fragment() {

    private lateinit var binding: FragmentServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentServiceBinding.inflate(inflater, container, false)

        binding.selectCarType.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(R.id.action_serviceFragment_to_chooseCarFragment)
        }

        return binding.root
    }
}