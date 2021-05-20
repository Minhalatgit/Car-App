package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.home.models.AppointmentResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentViewModel : ViewModel() {

    private val TAG = "AppointmentViewModel"

    private val _appointmentList = MutableLiveData<List<AppointmentResponse>>()
    val appointmentList: LiveData<List<AppointmentResponse>>
        get() = _appointmentList

    private val _noService = MutableLiveData<Boolean>()
    val noService: LiveData<Boolean>
        get() = _noService

    fun getAppointments() {
        RetrofitClient.apiInterface.getAppointments()
            .enqueue(object : Callback<BaseResponse<List<AppointmentResponse>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<AppointmentResponse>>>,
                    response: Response<BaseResponse<List<AppointmentResponse>>>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()?.data}")
                    response.body()?.apply {
                        _appointmentList.value = data!!
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<List<AppointmentResponse>>>,
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