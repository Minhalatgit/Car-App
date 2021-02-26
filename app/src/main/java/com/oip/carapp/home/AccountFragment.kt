package com.oip.carapp.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentAccountBinding
import com.oip.carapp.databinding.FragmentSettingsBinding
import com.oip.carapp.databinding.FragmentWalletBinding
import com.oip.carapp.home.adapters.PaymentHistoryAdapter
import com.oip.carapp.home.models.PaymentHistory

class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAccountBinding.inflate(inflater, container, false)


        return binding.root
    }
}