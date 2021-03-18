package com.oip.carapp.authentication.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.authentication.repo.LoginRepository
import com.oip.carapp.retrofit.BaseResponse

class LoginViewModel : ViewModel() {

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean>
        get() = _isValid

    fun login(email: String, password: String): MutableLiveData<BaseResponse<AuthResponse>> {
        return LoginRepository.login(email, password)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("LoginViewModel", "onCleared: ")
    }
}