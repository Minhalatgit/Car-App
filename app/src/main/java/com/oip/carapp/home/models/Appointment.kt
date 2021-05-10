package com.oip.carapp.home.models

data class Appointment(
    val profileImage: String,
    val username: String,
    val amount: String,
    val date: String,
    val service: String,
    val provider: String,
    val time: String
)