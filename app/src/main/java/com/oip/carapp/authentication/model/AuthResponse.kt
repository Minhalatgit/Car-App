package com.oip.carapp.authentication.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("id") val id : String,
    @SerializedName("email") val email : String,
    @SerializedName("name") val name : String,
    @SerializedName("image") val image : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("address") val address : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("birthday") val birthday : String,
    @SerializedName("member_type") val member_type : String
)