package com.oip.carapp.home.models

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    @SerializedName("id")
    val appointmentId: String,
    @SerializedName("service_id")
    val serviceId: String,
    @SerializedName("user_id")
    val userId: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("amount")
    val amount: String,
    @SerializedName("appointment_date")
    val appointmentDate: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("cancelled_date")
    val cancelledDate: String
)