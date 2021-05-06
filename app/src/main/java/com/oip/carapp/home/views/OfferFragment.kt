package com.oip.carapp.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentOfferBinding
import com.oip.carapp.home.adapters.DiscountAdapter
import com.oip.carapp.home.models.Discount

class OfferFragment : BaseFragment(), DiscountAdapter.DiscountListener {

    private val TAG = "OfferFragment"

    private lateinit var binding: FragmentOfferBinding
    private val discountList = ArrayList<Discount>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        discountList.add(Discount("image url", "Engine Analysis", "20%"))
        discountList.add(Discount("image url", "Engine Analysis", "20%"))
        discountList.add(Discount("image url", "Engine Analysis", "20%"))
        discountList.add(Discount("image url", "Engine Analysis", "20%"))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOfferBinding.inflate(layoutInflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)

        binding.discountList.layoutManager = LinearLayoutManager(activity)
        binding.discountList.adapter =
            DiscountAdapter(discountList, requireContext(), this)

        return binding.root
    }

    override fun onDiscountClick(position: Int) {
        Log.d(TAG, "onDiscountClick: $position")
    }
}