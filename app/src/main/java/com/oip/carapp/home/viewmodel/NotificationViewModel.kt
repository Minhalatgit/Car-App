package com.oip.carapp.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oip.carapp.BaseViewModel
import com.oip.carapp.home.models.NotificationResponse
import com.oip.carapp.retrofit.RetrofitClient
import kotlinx.coroutines.launch

class NotificationViewModel : BaseViewModel() {

    private val TAG = "NotificationViewModel"

    private val _notificationList = MutableLiveData<List<NotificationResponse>>()
    val notificationList: LiveData<List<NotificationResponse>>
        get() = _notificationList

    private val _noService = MutableLiveData<Boolean>()
    val noService: LiveData<Boolean>
        get() = _noService

    fun getNotifications() {
        coroutineScope.launch {
            try {
                val getNotificationsResult = RetrofitClient.apiInterface.getNotifications()
                Log.d(TAG, "onResponse: ${getNotificationsResult.data}")
                getNotificationsResult.apply {
                    _notificationList.value = data!!
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