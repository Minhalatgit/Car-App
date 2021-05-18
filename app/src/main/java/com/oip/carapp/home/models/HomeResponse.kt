package com.oip.carapp.home.models

import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("offers")
    val offers: List<OfferResponse>,
    @SerializedName("services")
    val services: List<ServiceResponse>
)