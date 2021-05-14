package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceListViewModel() : ViewModel() {

    private val TAG = "ServiceListViewModel"

    private val _serviceList = MutableLiveData<List<ServiceResponse>>()
    val serviceList: LiveData<List<ServiceResponse>>
        get() = _serviceList

    private val _noService = MutableLiveData<Boolean>()
    val noService: LiveData<Boolean>
        get() = _noService

    fun getServices(catId: String) {
        RetrofitClient.apiInterface.getServices(catId)
            .enqueue(object : Callback<BaseResponse<List<ServiceResponse>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<ServiceResponse>>>,
                    response: Response<BaseResponse<List<ServiceResponse>>>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()?.data}")
                    response.body()?.apply {
                        _serviceList.value = data!!
                        _noService.value = data.isEmpty()
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<List<ServiceResponse>>>,
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