package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import com.oip.carapp.utils.PreferencesHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {

    private val TAG = "ProfileViewModel"

    private val _profileData = MutableLiveData<AuthResponse>()
    val profileData: LiveData<AuthResponse>
        get() = _profileData

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

    init {
        Log.d(TAG, "ViewModel initialized")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ViewModel destroyed")
    }
}