package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.BaseViewModel
import com.oip.carapp.home.models.StoreResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreViewModel : BaseViewModel() {

    private val TAG = "StoreViewModel"

    private val _storeList = MutableLiveData<List<StoreResponse>>()
    val storeList: LiveData<List<StoreResponse>>
        get() = _storeList

    fun getStores() {
        coroutineScope.launch {
            try {
                val getStoresResult = RetrofitClient.apiInterface.getStores()
                Log.d(TAG, "onResponse: ${getStoresResult.data}")
                getStoresResult.apply {
                    _storeList.value = data!!
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