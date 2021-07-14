package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.BaseViewModel
import com.oip.carapp.home.models.OfferResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferViewModel : BaseViewModel() {

    private val TAG = "OfferListViewModel"

    private val _offerList = MutableLiveData<List<OfferResponse>>()
    val offerList: LiveData<List<OfferResponse>>
        get() = _offerList

    private val _noOffer = MutableLiveData<Boolean>()
    val noOffer: LiveData<Boolean>
        get() = _noOffer

    fun getOffers() {
        coroutineScope.launch {
            try {
                val getOffersResult = RetrofitClient.apiInterface.getOffers()
                Log.d(TAG, "onResponse: ${getOffersResult.data}")
                getOffersResult.apply {
                    _offerList.value = data!!
                    _noOffer.value = data.isEmpty()
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