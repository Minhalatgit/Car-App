package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentChooseCarBinding
import com.oip.carapp.home.adapters.CarViewPagerAdapter

class ChooseCarFragment : BaseFragment() {

    private lateinit var binding: FragmentChooseCarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentChooseCarBinding.inflate(inflater, container, false)

        val list = arrayListOf("All Car", "Sedan", "Hatchback", "Pick Up Truck")
        binding.viewPager.adapter =
            CarViewPagerAdapter(
                list,
                requireActivity()
            )
        TabLayoutMediator(
            binding.tab,
            binding.viewPager,
            TabConfigurationStrategy { tab: TabLayout.Tab, position: Int ->
                tab.text = list[position]
            }).attach()

        binding.proceed.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                .navigate(R.id.action_chooseCarFragment_to_paymentFragment)
        }

        setToolbarViews()

        return binding.root
    }

    private fun setToolbarViews() {
        title.text = "Choose Car"
        title.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
        switch.visibility = View.GONE
        navigationIcon.visibility = View.GONE
        mactivity.window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.dark)
    }
}