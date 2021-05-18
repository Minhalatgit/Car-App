package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.home.models.HomeResponse
import com.oip.carapp.home.models.OfferResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val TAG = "HomeViewModel"

    private val _homeResponse = MutableLiveData<HomeResponse>()
    val homeResponse: LiveData<HomeResponse>
        get() = _homeResponse

    fun getHomeData() {
        RetrofitClient.apiInterface.getHomeData()
            .enqueue(object : Callback<BaseResponse<HomeResponse>> {
                override fun onResponse(
                    call: Call<BaseResponse<HomeResponse>>,
                    response: Response<BaseResponse<HomeResponse>>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()?.data}")
                    response.body()?.apply {
                        _homeResponse.value = data!!
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<HomeResponse>>,
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