package com.oip.carapp.home.models

data class History(
    val profileImage: Int,
    val username: String,
    val amount: String,
    val distance: String,
    val pickupAddress: String,
    val dropOffAddress: String
)