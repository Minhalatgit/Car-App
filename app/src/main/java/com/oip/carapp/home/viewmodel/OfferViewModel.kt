package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.home.models.OfferResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OfferViewModel : ViewModel() {

    private val TAG = "OfferListViewModel"

    private val _offerList = MutableLiveData<List<OfferResponse>>()
    val offerList: LiveData<List<OfferResponse>>
        get() = _offerList

    private val _noOffer = MutableLiveData<Boolean>()
    val noOffer: LiveData<Boolean>
        get() = _noOffer

    fun getOffers() {
        RetrofitClient.apiInterface.getOffers()
            .enqueue(object : Callback<BaseResponse<List<OfferResponse>>> {
                override fun onResponse(
                    call: Call<BaseResponse<List<OfferResponse>>>,
                    response: Response<BaseResponse<List<OfferResponse>>>
                ) {
                    Log.d(TAG, "onResponse: ${response.body()?.data}")
                    response.body()?.apply {
                        _offerList.value = data!!
                        _noOffer.value = data.isEmpty()
                    }
                }

                override fun onFailure(
                    call: Call<BaseResponse<List<OfferResponse>>>,
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