package com.oip.carapp.retrofit

data class BaseResponse<T>(
    val status: Boolean,
    val msg: String,
    val data: T
)