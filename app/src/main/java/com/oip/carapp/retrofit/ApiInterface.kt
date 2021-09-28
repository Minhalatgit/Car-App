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
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): BaseResponse<AuthResponse>

    @FormUrlEncoded
    @POST("auth/register")
    suspend fun register(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("confirm_password") confirmPassword: String
    ): BaseResponse<AuthResponse>

    @FormUrlEncoded
    @POST("auth/verify")
    fun verifyCode(
        @Field("email") email: String,
        @Field("verification_code") code: String
    ): Call<BaseResponse<AuthResponse>>

    @FormUrlEncoded
    @POST("service/getClientServices")
    suspend fun getServices(
        @Field("cat_id") catId: String
    ): BaseResponse<ArrayList<ServiceResponse>>

    @FormUrlEncoded
    @POST("service/favouriteservice")
    suspend fun updateFavourite(
        @Field("service_id") serviceId: String,
        @Field("is_favourite") isFavourite: String
    ): BaseResponse<Unit>

    @POST("offer/getclientoffers")
    suspend fun getOffers(): BaseResponse<List<OfferResponse>>

    @POST("store/getclientstores")
    suspend fun getStores(): BaseResponse<List<StoreResponse>>

    @FormUrlEncoded
    @POST("profile/getprofile")
    suspend fun getProfile(@Field("profile_id") userId: String): BaseResponse<AuthResponse>

    @Multipart
    @POST("profile/updateprofile")
    suspend fun updateProfile(
        @Part("user_name") username: RequestBody,
        @Part("user_number") phone: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("birthday") birthday: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("profile_id") profileId: RequestBody
    ): BaseResponse<AuthResponse>

    @Multipart
    @POST("profile/updateprofile")
    suspend fun updateProfile(
        @Part("user_name") username: RequestBody,
        @Part("user_number") phone: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("birthday") birthday: RequestBody,
        @Part("profile_id") profileId: RequestBody
    ): BaseResponse<AuthResponse>

    @POST("home/gethome")
    suspend fun getHomeData(): BaseResponse<HomeResponse>

    @POST("appointment/getappointments")
    suspend fun getAppointments(): BaseResponse<List<AppointmentResponse>>

    @POST("notification/getnotifications")
    suspend fun getNotifications(): BaseResponse<List<NotificationResponse>>

    @FormUrlEncoded
    @POST("service/bookservice")
    suspend fun bookService(
        @Field("service_id") serviceId: String,
        @Field("appointment_date") appointmentDate: String
    ): BaseResponse<Unit>

}