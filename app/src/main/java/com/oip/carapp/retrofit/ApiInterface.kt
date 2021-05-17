package com.oip.carapp.retrofit

import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.home.models.OfferResponse
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.home.models.StoreResponse
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

    @FormUrlEncoded
    @POST("service/getservices")
    fun getServices(
        @Field("cat_id") catId: String
    ): Call<BaseResponse<List<ServiceResponse>>>

    @POST("offer/getoffers")
    fun getOffers(): Call<BaseResponse<List<OfferResponse>>>

    @POST("store/getstores")
    fun getStores(): Call<BaseResponse<List<StoreResponse>>>

    @FormUrlEncoded
    @POST("profile/getprofile")
    fun getProfile(@Field("profile_id") userId: String): Call<BaseResponse<AuthResponse>>
}