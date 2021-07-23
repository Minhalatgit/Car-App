package com.oip.carapp.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oip.carapp.R
import com.oip.carapp.home.models.OfferResponse
import com.oip.carapp.utils.Constants.BASE_URL_IMAGES
import com.oip.carapp.utils.loadImage
import com.squareup.picasso.Picasso

class DiscountAdapter(
    private val list: List<OfferResponse>,
    val context: Context,
    private val listener: DiscountListener
) :
    RecyclerView.Adapter<DiscountAdapter.DiscountViewHolder>() {

    inner class DiscountViewHolder(itemView: View, listener: DiscountListener) :
        RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.image)
        val name: TextView = itemView.findViewById(R.id.name)
        val percentage: TextView = itemView.findViewById(R.id.percentage)

        init {
            itemView.setOnClickListener {
                listener.onDiscountClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DiscountViewHolder(
        LayoutInflater.from(context).inflate(R.layout.discount_item, parent, false), listener
    )

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: DiscountViewHolder, position: Int) {
        val discount = list[position]

        holder.apply {
            name.text = discount.offerTitle
            percentage.text = discount.offerDiscount + "%"
            context.loadImage(image, discount.offerImage)
        }

    }

    interface DiscountListener {
        fun onDiscountClick(position: Int)
    }
}