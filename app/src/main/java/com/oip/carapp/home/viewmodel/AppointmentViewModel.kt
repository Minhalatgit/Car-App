package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.BaseViewModel
import com.oip.carapp.home.models.AppointmentResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentViewModel : BaseViewModel() {

    private val TAG = "AppointmentViewModel"

    private val _appointmentList = MutableLiveData<List<AppointmentResponse>>()
    val appointmentList: LiveData<List<AppointmentResponse>>
        get() = _appointmentList

    private val _noService = MutableLiveData<Boolean>()
    val noService: LiveData<Boolean>
        get() = _noService

    fun getAppointments() {
        coroutineScope.launch {
            try {
                val getAppointmentsResult = RetrofitClient.apiInterface.getAppointments()
                Log.d(TAG, "onResponse: ${getAppointmentsResult.data}")
                getAppointmentsResult.apply {
                    _appointmentList.value = data!!
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