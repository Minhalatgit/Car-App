package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import com.oip.carapp.utils.PreferencesHandler
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File


class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"

    private val _profileData = MutableLiveData<AuthResponse>()
    val profileData: LiveData<AuthResponse>
        get() = _profileData

    private val _updatedProfileData = MutableLiveData<AuthResponse>()
    val updatedProfileData: LiveData<AuthResponse>
        get() = _updatedProfileData

    fun getProfile() {
        RetrofitClient.apiInterface.getProfile(PreferencesHandler.getUserId()!!)
            .enqueue(object : Callback<BaseResponse<AuthResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<AuthResponse>>,
                    response: Response<BaseResponse<AuthResponse>>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()?.data}")
                    response.body()?.apply {
                        _profileData.value = data!!
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<AuthResponse>>,
                    t: Throwable
                ) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    fun updateProfile(
        username: String,
        phone: String,
        gender: String,
        birthday: String,
        profileId: String,
        profileImage: File
    ) {
        val usernameReq = username.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val phoneReq = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val genderReq = gender.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val birthdayReq = birthday.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val profileIdReq = profileId.toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val imageFile = profileImage.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val multipartBody =
            MultipartBody.Part.createFormData("profile_image", profileImage.name, imageFile)

        RetrofitClient.apiInterface.updateProfile(
            usernameReq,
            phoneReq,
            genderReq,
            birthdayReq,
            multipartBody,
            profileIdReq
        )
            .enqueue(object : Callback<BaseResponse<AuthResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<AuthResponse>>,
                    response: Response<BaseResponse<AuthResponse>>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()?.data}")
                    response.body()?.apply {
                        PreferencesHandler.setUsername(data.name)
                        PreferencesHandler.setProfileImageUrl(data.image)
                        _updatedProfileData.value = data!!
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<AuthResponse>>,
                    t: Throwable
                ) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            })
    }

    init {
        Log.d(TAG, "ViewModel initialized")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ViewModel destroyed")
    }
}