package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentSettingsBinding

class SettingsFragment : BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        setToolbarView()

        binding.profileLayout.setOnClickListener {
            it.findNavController().navigate(R.id.action_settingsFragment_to_accountFragment)
        }

        return binding.root
    }

    private fun setToolbarView() {
        title.text = "Settings"
        switch.visibility = View.GONE
        navigationIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.yellow))
        mactivity.window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }
}