package com.oip.carapp.home.models

import com.google.gson.annotations.SerializedName

data class ServiceResponse(
    @SerializedName("id") val id: String,
    @SerializedName("cat_id") val cat_id: String,
    @SerializedName("service_title") val serviceTitle: String,
    @SerializedName("service_subtitle") val serviceSubtitle: String,
    @SerializedName("service_distance") val serviceDistance: String,
    @SerializedName("is_favourite") val isFavourite: String,
    @SerializedName("service_image") val serviceImage: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("is_deleted") val isDeleted: String
)