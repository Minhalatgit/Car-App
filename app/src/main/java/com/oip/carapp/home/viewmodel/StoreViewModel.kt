package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.home.models.StoreResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel : ViewModel() {

    private val TAG = "StoreViewModel"

    private val _storeList = MutableLiveData<List<StoreResponse>>()
    val storeList: LiveData<List<StoreResponse>>
        get() = _storeList

    fun getStores() {
        RetrofitClient.apiInterface.getStores()
            .enqueue(object : Callback<BaseResponse<List<StoreResponse>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<StoreResponse>>>,
                    response: Response<BaseResponse<List<StoreResponse>>>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()?.data}")
                    response.body()?.apply {
                        _storeList.value = data!!
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<List<StoreResponse>>>,
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