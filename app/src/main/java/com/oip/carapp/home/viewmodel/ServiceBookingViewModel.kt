package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.BaseViewModel
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ServiceBookingViewModel : BaseViewModel() {

    private val TAG = "ServiceBookingViewModel"

    private val _serviceBooking = MutableLiveData<List<ServiceResponse>>()
    val serviceBooking: LiveData<List<ServiceResponse>>
        get() = _serviceBooking

    fun bookService(catId: String) {
        coroutineScope.launch {
            try {
                val getServicesResult = RetrofitClient.apiInterface.getServices(catId)
                Log.d(TAG, "onResponse: ${getServicesResult.data}")
                getServicesResult.apply {
                    _serviceBooking.value = data!!
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