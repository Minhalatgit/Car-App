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

    init {
        Log.d(TAG, "ViewModel initialized")
        //_favouriteUpdated.value = false
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

    fun favouriteEventCompleted() {

    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.d(TAG, "onCleared: ViewModel destroyed")
    }
}