package com.oip.carapp.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentPaymentBinding

class PaymentFragment : BaseFragment() {

    private lateinit var binding: FragmentPaymentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        setToolbarViews()

        return binding.root
    }

    private fun setToolbarViews() {
        title.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark))
        title.text = "Payment Fragment"
        switch.visibility = View.GONE
        navigationIcon.visibility = View.GONE
        mactivity.window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }
}