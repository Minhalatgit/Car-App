package com.oip.carapp.retrofit

import com.oip.carapp.authentication.model.AuthResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String
    ): Call<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("auth/verify")
    fun verifyCode(
        @Field("email") email: String,
        @Field("verification_code") code: String
    ): Call<BaseResponse<AuthResponse>>
}