package com.oip.carapp.home.models

import com.google.gson.annotations.SerializedName

data class StoreResponse(
    @SerializedName("id") val id: String,
    @SerializedName("store_title") val storeTitle: String,
    @SerializedName("store_address") val storeAddress: String,
    @SerializedName("store_lat") val storeLatitude: Double,
    @SerializedName("store_long") val storeLongitude: Double,
    @SerializedName("store_image") val storeImage: String,
    @SerializedName("is_deleted") val isDeleted: String
)