package com.oip.carapp.home.models

import com.google.gson.annotations.SerializedName

data class NotificationResponse(

    @SerializedName("id")
    val notificationId: String,

    @SerializedName("notification_title")
    val title: String,

    @SerializedName("notification_content")
    val content: String,

    @SerializedName("notification_image")
    val notificationImage: String
)