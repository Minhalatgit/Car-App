package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.BaseViewModel
import com.oip.carapp.home.models.HomeResponse
import com.oip.carapp.home.models.OfferResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : BaseViewModel() {

    private val TAG = "HomeViewModel"

    private val _homeResponse = MutableLiveData<HomeResponse>()
    val homeResponse: LiveData<HomeResponse>
        get() = _homeResponse

    fun getHomeData() {
        coroutineScope.launch {
            try {
                val getHomeDataResult = RetrofitClient.apiInterface.getHomeData()
                Log.d(TAG, "onResponse: ${getHomeDataResult.data}")
                getHomeDataResult.apply {
                    _homeResponse.value = data!!
                }
            } catch (t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
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