package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oip.carapp.BaseViewModel
import com.oip.carapp.authentication.model.Result
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class ServiceListViewModel : BaseViewModel() {

    private val TAG = "ServiceListViewModel"

    private val _serviceList = MutableLiveData<ArrayList<ServiceResponse>>()
    val serviceList: LiveData<ArrayList<ServiceResponse>>
        get() = _serviceList

    private val _noService = MutableLiveData<Boolean>()
    val noService: LiveData<Boolean>
        get() = _noService

    private val _favouriteUpdated = MutableLiveData<Boolean>()
    val favouriteUpdated: LiveData<Boolean>
        get() = _favouriteUpdated

    private val _bookingResult = MutableLiveData<Result>()
    val bookingResult: LiveData<Result>
        get() = _bookingResult

    init {
        Log.d(TAG, "ViewModel initialized")
    }

    fun getServices(catId: String) {
        coroutineScope.launch {
            try {
                val getServicesResult = RetrofitClient.apiInterface.getServices(catId)
                Log.d(TAG, "onResponse: ${getServicesResult.data}")
                getServicesResult.apply {
                    _serviceList.value = data!!
                    _noService.value = data.isEmpty()
                }
            } catch (t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        }
    }

    fun updateFavourite(serviceId: String, isFavourite: String) {
        coroutineScope.launch {
            try {
                val updateFavouriteResult =
                    RetrofitClient.apiInterface.updateFavourite(serviceId, isFavourite)
                updateFavouriteResult.apply {
                    _favouriteUpdated.value = isFavourite == "1"
                    Log.d(TAG, "updateFavourite: ${updateFavouriteResult.msg}")
                }
            } catch (t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        }
    }

    fun bookService(serviceId: String, appointmentDate: String) {
        coroutineScope.launch {
            try {
                val bookingServiceResult =
                    RetrofitClient.apiInterface.bookService(serviceId, appointmentDate)
                bookingServiceResult.apply {
                    _bookingResult.value = Result(status, msg)
                    Log.d(TAG, "bookService: $msg")
                }
            } catch (t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message}")
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.d(TAG, "onCleared: ViewModel destroyed")
    }
}