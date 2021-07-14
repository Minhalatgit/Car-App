package com.oip.carapp.home.models

import com.google.gson.annotations.SerializedName

data class AppointmentResponse(
    //Appointment data
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
    val cancelledDate: String,

    //Service data
    @SerializedName("cat_id")
    val categoryId: String,
    @SerializedName("service_title")
    val serviceTitle: String,
    @SerializedName("service_subtitle")
    val serviceSubtitle: String,
    @SerializedName("service_amount")
    val serviceAmount: String,
    @SerializedName("is_favourite")
    val isFavourite: String,
    @SerializedName("service_image")
    val serviceImage: String,
    @SerializedName("updated_at")
    val serviceUpdatedAt: String,
    @SerializedName("is_deleted")
    val isServiceDeleted: String,

    //Store data
    @SerializedName("store_title")
    val storeTitle: String,
    @SerializedName("store_address")
    val storeAddress: String,
    @SerializedName("store_lat")
    val storeLat: String,
    @SerializedName("store_long")
    val storeLong: String,
    @SerializedName("store_image")
    val storeImage: String
)