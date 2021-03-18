package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceBinding

class ServiceFragment : BaseFragment() {

    private lateinit var binding: FragmentServiceBinding

    lateinit var args: ServiceFragmentArgs

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
        args =
            ServiceFragmentArgs.fromBundle(requireArguments())

        setToolbarViews()

        return binding.root
    }

    private fun setToolbarViews() {
        title.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark))
        title.text = "${args.serviceName} Service"
        switch.visibility = View.GONE
        navigationIcon.visibility = View.GONE
        mactivity.window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }
}