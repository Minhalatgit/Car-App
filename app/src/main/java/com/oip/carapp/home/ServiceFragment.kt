package com.oip.carapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceBinding
import com.suke.widget.SwitchButton

class ServiceFragment : Fragment() {

    private lateinit var binding: FragmentServiceBinding
    lateinit var toolbar: Toolbar
    lateinit var title: TextView
    lateinit var switch: SwitchButton

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
        args = ServiceFragmentArgs.fromBundle(requireArguments())

        setToolbarViews()

        return binding.root
    }

    private fun setToolbarViews() {
        toolbar = activity?.findViewById(R.id.toolbar)!!
        title = toolbar.findViewById(R.id.title)
        switch = toolbar.findViewById(R.id.switch_button)
        title.text = "${args.serviceName} Service"
        switch.visibility = View.GONE
    }
}