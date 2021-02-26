package com.oip.carapp.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.PaymentHistory
import kotlinx.android.synthetic.main.payment_history_item.view.*

class PaymentHistoryAdapter(private val list: ArrayList<PaymentHistory>) :
    RecyclerView.Adapter<PaymentHistoryAdapter.PaymentHistoryHolder>() {

    inner class PaymentHistoryHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PaymentHistoryHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.payment_history_item, parent, false)
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PaymentHistoryHolder, position: Int) {
        val paymentHistory = list[position]

        holder.itemView.apply {
            profileImage.setImageResource(paymentHistory.profileImage)
            name.text = paymentHistory.name
            historyId.text = paymentHistory.historyId
            amount.text = paymentHistory.historyId
        }
    }
}