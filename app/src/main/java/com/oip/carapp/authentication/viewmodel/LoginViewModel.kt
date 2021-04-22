package com.oip.carapp.authentication.viewmodel

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.authentication.model.Result
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import com.oip.carapp.utils.PreferencesHandler
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val TAG = "LoginViewModel"

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result>
        get() = _result

    init {
        Log.d(TAG, "Login view model initialized")
    }

    fun login(email: String, password: String) {
        val validity = checkForValidity(email, password)
        if (validity.isValid) {
            Log.d(TAG, validity.message)
            RetrofitClient.apiInterface.login(email, password)
                .enqueue(object : Callback<BaseResponse<AuthResponse>> {
                    override fun onFailure(
                        call: Call<BaseResponse<AuthResponse>>,
                        t: Throwable
                    ) {
                        _result.value = Result(false, t.message!!)
                        Log.e(TAG, t.message!!)
                    }

                    override fun onResponse(
                        call: Call<BaseResponse<AuthResponse>>,
                        response: Response<BaseResponse<AuthResponse>>
                    ) {
                        response.body()?.apply {
                            if (status) {
                                _result.value =
                                    Result(true, msg)
                                Log.d(TAG, msg)
                                PreferencesHandler.setIsLogin(true)
                            } else {
                                _result.value =
                                    Result(false, msg)
                                Log.d(TAG, msg)
                            }
                        }
                    }
                })
        } else {
            Log.d(TAG, validity.message)
            _result.value = validity
        }
    }

    private fun checkForValidity(email: String, password: String): Result {
        if (TextUtils.isEmpty(email)) {
            return Result(false, "Email must not be empty")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result(false, "Email is invalid")
        }

        if (TextUtils.isEmpty(password)) {
            return Result(false, "Password must not be empty")
        } else if (password.length < 6) {
            return Result(false, "Password is short")
        }
        return Result(true, "Success")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }
}
