package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceListBinding
import com.oip.carapp.home.adapters.ServicePagerAdapter

class ServiceListFragment : BaseFragment() {

    private lateinit var binding: FragmentServiceListBinding
    lateinit var args: ServiceListFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServiceListBinding.inflate(layoutInflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)

        args = ServiceListFragmentArgs.fromBundle(requireArguments())
        binding.title.text = args.serviceType

        val list = arrayListOf("Oil", "Engine", "Tune", "Belt", "Fluid")
        binding.viewPager.adapter = ServicePagerAdapter(list, requireActivity())
        TabLayoutMediator(
            binding.tab,
            binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = list[position]
        }.attach()

        return binding.root
    }

}