package com.oip.carapp.retrofit

import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.home.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

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
    @POST("service/getClientServices")
    fun getServices(
        @Field("cat_id") catId: String
    ): Call<BaseResponse<List<ServiceResponse>>>

    @POST("offer/getclientoffers")
    fun getOffers(): Call<BaseResponse<List<OfferResponse>>>

    @POST("store/getclientstores")
    fun getStores(): Call<BaseResponse<List<StoreResponse>>>

    @FormUrlEncoded
    @POST("profile/getprofile")
    fun getProfile(@Field("profile_id") userId: String): Call<BaseResponse<AuthResponse>>

    @Multipart
    @POST("profile/updateprofile")
    fun updateProfile(
        @Part("user_name") username: RequestBody,
        @Part("user_number") phone: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("birthday") birthday: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("profile_id") profileId: RequestBody
    ): Call<BaseResponse<AuthResponse>>

    @POST("home/gethome")
    fun getHomeData(): Call<BaseResponse<HomeResponse>>

    @POST("appointment/getappointments")
    fun getAppointments(): Call<BaseResponse<List<AppointmentResponse>>>
}