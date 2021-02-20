package com.oip.carapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentPaymentBinding
import com.suke.widget.SwitchButton

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding
    lateinit var toolbar: Toolbar
    lateinit var title: TextView
    lateinit var switch: SwitchButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        setToolbarViews()

        return binding.root
    }

    private fun setToolbarViews() {
        toolbar = activity?.findViewById(R.id.toolbar)!!
        title = toolbar.findViewById(R.id.title)
        switch = toolbar.findViewById(R.id.switch_button)
        title.setTextColor(ContextCompat.getColor(requireContext(), R.color.dark))
        title.text = "Payment Fragment"
        switch.visibility = View.GONE
    }
}