package com.oip.carapp.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentWalletBinding
import com.oip.carapp.home.adapters.PaymentHistoryAdapter
import com.oip.carapp.home.models.PaymentHistory

class WalletFragment : BaseFragment() {

    private lateinit var binding: FragmentWalletBinding
    private lateinit var paymentHistoryRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletBinding.inflate(inflater, container, false)

        setToolbarView()

        binding.tab.addTab(binding.tab.newTab().setText("Cash"))
        binding.tab.addTab(binding.tab.newTab().setText("Discount"))

        paymentHistoryRecyclerView = binding.paymentHistoryRecyclerView
        paymentHistoryRecyclerView.layoutManager = LinearLayoutManager(activity)

        val list = ArrayList<PaymentHistory>()
        list.add(PaymentHistory(R.drawable.profile, "Elva Barnett", "#740136", "$25.00"))
        list.add(PaymentHistory(R.drawable.profile_history, "Isaiah Francis", "#539642", "12.00"))
        list.add(PaymentHistory(R.drawable.profile_two, "Lula Briggs", "#123146", "$25.00"))
        list.add(PaymentHistory(R.drawable.profile_history, "Ray Young", "#521936", "$25.00"))
        list.add(PaymentHistory(R.drawable.profile, "Ray Young", "#521936", "$25.00"))
        list.add(PaymentHistory(R.drawable.profile_two, "Ray Young", "#521936", "$25.00"))

        paymentHistoryRecyclerView.adapter = PaymentHistoryAdapter(list)

        return binding.root
    }

    private fun setToolbarView() {
        title.text = "My Wallet"
        switch.visibility = View.GONE
        navigationIcon.setColorFilter(ContextCompat.getColor(requireContext(), R.color.dark))
        mactivity.window.statusBarColor = ContextCompat.getColor(requireContext(), R.color.yellow)
    }
}