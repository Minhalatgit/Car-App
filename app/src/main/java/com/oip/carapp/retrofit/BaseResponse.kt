package com.oip.carapp.retrofit

data class BaseResponse<T>(
    val success: Boolean,
    val message: String,
    val data: T
)