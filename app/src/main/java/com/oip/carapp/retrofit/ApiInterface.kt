package com.oip.carapp.retrofit

import com.oip.carapp.authentication.model.AuthResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("number") number: String
    ): Call<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("verify_code")
    fun verifyCode(
        @Field("email") email: String,
        @Field("code") code: String
    ): Call<BaseResponse<AuthResponse>>
}