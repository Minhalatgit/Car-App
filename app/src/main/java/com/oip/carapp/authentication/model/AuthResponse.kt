package com.oip.carapp.authentication.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("id") val id: String,
    @SerializedName("email") val email: String,
    @SerializedName("username") val name: String,
    @SerializedName("user_img") val image: String,
    @SerializedName("user_number") val phone: String,
    @SerializedName("user_address") val address: String,
    @SerializedName("gender") val gender: String,
    @SerializedName("birthday") val birthday: String,
    @SerializedName("member_type") val memberType: String,
    @SerializedName("token") val token: String,
    @SerializedName("status") val status: Int
)