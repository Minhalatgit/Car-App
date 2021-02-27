package com.oip.carapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentAccountBinding
import com.oip.carapp.databinding.FragmentSettingsBinding
import com.oip.carapp.databinding.FragmentWalletBinding
import com.oip.carapp.home.adapters.PaymentHistoryAdapter
import com.oip.carapp.home.models.PaymentHistory

class AccountFragment : BaseFragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)

        toolbar.visibility = View.GONE

        binding.back.setOnClickListener {
            it.findNavController().navigate(R.id.action_accountFragment_to_settingsFragment)
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        toolbar.visibility = View.VISIBLE
    }
}