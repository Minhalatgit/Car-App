package com.oip.carapp.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentOfferBinding
import com.oip.carapp.home.adapters.DiscountAdapter
import com.oip.carapp.home.models.OfferResponse
import com.oip.carapp.home.viewmodel.OfferViewModel
import com.oip.carapp.utils.PreferencesHandler
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar

class OfferFragment : BaseFragment(), DiscountAdapter.DiscountListener {

    private val TAG = "OfferFragment"

    private lateinit var binding: FragmentOfferBinding
    private val offers = ArrayList<OfferResponse>()
    private lateinit var viewModel: OfferViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOfferBinding.inflate(layoutInflater, container, false)
        binding.offerList.layoutManager = LinearLayoutManager(activity)

        viewModel = ViewModelProvider(this).get(OfferViewModel::class.java)

        showProgressBar(window, binding.progress)
        viewModel.getOffers()

        viewModel.offerList.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty()) {
                PreferencesHandler.setOffer(it[0].offerDiscount)
                binding.offer.text = "FLAT ${it[0].offerDiscount}% OFF"
                binding.noOffer.visibility = View.GONE
            } else {
                binding.noOffer.visibility = View.VISIBLE
            }
            hideProgressBar(window, binding.progress)
            binding.offerList.adapter =
                DiscountAdapter(it, requireContext(), this)
        })

        return binding.root
    }

    override fun onDiscountClick(position: Int) {
        Log.d(TAG, "onDiscountClick: $position")
    }
}