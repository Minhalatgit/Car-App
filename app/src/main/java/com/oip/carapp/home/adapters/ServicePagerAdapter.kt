package com.oip.carapp.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oip.carapp.home.views.*

class ServicePagerAdapter(private val titles: ArrayList<String>, val fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OilFragment()
            1 -> EngineFragment()
            2 -> TuneFragment()
            3 -> BeltFragment()
            4 -> FluidFragment()
            else -> OilFragment()
        }
    }
}