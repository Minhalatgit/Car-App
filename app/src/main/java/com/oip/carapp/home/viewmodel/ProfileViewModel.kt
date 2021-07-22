package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oip.carapp.BaseViewModel
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.authentication.model.Result
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import com.oip.carapp.utils.PreferencesHandler
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class ProfileViewModel : BaseViewModel() {

    private val TAG = "ProfileViewModel"

    private val _profileData = MutableLiveData<AuthResponse>()
    val profileData: LiveData<AuthResponse>
        get() = _profileData

    private val _updatedProfileData = MutableLiveData<AuthResponse>()
    val updatedProfileData: LiveData<AuthResponse>
        get() = _updatedProfileData

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result>
        get() = _result

    fun getProfile() {
        coroutineScope.launch {
            try {
                val getProfileResult =
                    RetrofitClient.apiInterface.getProfile(PreferencesHandler.getUserId()!!)
                Log.d(TAG, "onResponse: ${getProfileResult.data}")
                getProfileResult.apply {
                    _profileData.value = data!!
                }
            } catch (t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        }
    }

    fun updateProfile(
        username: String,
        phone: String,
        gender: String,
        birthday: String,
        profileId: String,
        profileImage: File?
    ) {

        if (username.isEmpty()) {
            _result.value = Result(false, "Username must not be empty")
        } else if (phone.isEmpty()) {
            _result.value = Result(false, "Phone number must not be empty")
        } else if (phone.length < 9) {
            _result.value = Result(false, "Phone number is not complete")
        } else {
            _result.value = Result(true, "")
            val usernameReq = username.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val phoneReq = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val genderReq = gender.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val birthdayReq = birthday.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            val profileIdReq = profileId.toRequestBody("multipart/form-data".toMediaTypeOrNull())

            var multipartBody: MultipartBody.Part? = null
            if (profileImage != null) {
                Log.d(TAG, "Profile image is not null")
                val imageFile =
                    profileImage.asRequestBody("multipart/form-data".toMediaTypeOrNull())
                multipartBody =
                    MultipartBody.Part.createFormData("profile_image", profileImage.name, imageFile)
            }

            coroutineScope.launch {
                try {
                    val updateProfileResult: BaseResponse<AuthResponse>
                    if (multipartBody == null) {
                        updateProfileResult = RetrofitClient.apiInterface.updateProfile(
                            usernameReq,
                            phoneReq,
                            genderReq,
                            birthdayReq,
                            profileIdReq
                        )
                    } else {
                        updateProfileResult = RetrofitClient.apiInterface.updateProfile(
                            usernameReq,
                            phoneReq,
                            genderReq,
                            birthdayReq,
                            multipartBody,
                            profileIdReq
                        )
                    }

                    if (updateProfileResult.status) {
                        Log.d(TAG, "onResponse: ${updateProfileResult.data}")
                        updateProfileResult.apply {
                            PreferencesHandler.setUsername(data.name ?: "")
                            PreferencesHandler.setProfileImageUrl(data.image ?: "")
                            _updatedProfileData.value = data!!
                        }
                    } else {
                        Log.e(TAG, "Error: ${updateProfileResult.msg}")
                    }

                } catch (t: Throwable) {
                    Log.e(TAG, "onFailure: ${t.message}")
                }
            }
        }
    }

    init {
        Log.d(TAG, "ViewModel initialized")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.d(TAG, "onCleared: ViewModel destroyed")
    }
}