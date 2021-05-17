package com.oip.carapp.home.models

import com.google.gson.annotations.SerializedName

data class OfferResponse(
    @SerializedName("id") val id: String,
    @SerializedName("offer_title") val offerTitle: String,
    @SerializedName("offer_discount") val offerDiscount: String,
    @SerializedName("offer_image") val offerImage: String
)