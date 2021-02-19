package com.oip.carapp.home.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oip.carapp.home.AllCarFragment
import com.oip.carapp.home.HatchbackFragment
import com.oip.carapp.home.PickUpTruckFragment
import com.oip.carapp.home.SedanFragment

class CarViewPagerAdapter(private val titles: ArrayList<String>, val fragment: FragmentActivity) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return titles.size
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllCarFragment()
            1 -> SedanFragment()
            2 -> HatchbackFragment()
            3 -> PickUpTruckFragment()
            else -> AllCarFragment()
        }
    }
}