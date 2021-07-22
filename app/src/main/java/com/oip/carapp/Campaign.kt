package com.oip.carapp

import com.google.gson.annotations.SerializedName

class Campaign(
    @SerializedName("api_status") val status: String = "",
    @SerializedName("message") val message: String = "",
    @SerializedName("data") val data: Data? = null
)