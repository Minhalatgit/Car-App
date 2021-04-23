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

class SignUpViewModel : ViewModel() {

    private val TAG = "SignUpViewModel"

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result>
        get() = _result

    init {
        Log.d(TAG, "SignUp view model initialized")
    }

    fun register(email: String, password: String, confirmPassword: String) {
        val validity = checkForValidity(email, password, confirmPassword)
        if (validity.isValid) {
            Log.d(TAG, validity.message)
            RetrofitClient.apiInterface.register(email, password, confirmPassword)
                .enqueue(object : Callback<BaseResponse<AuthResponse>> {
                    override fun onFailure(
                        call: Call<BaseResponse<AuthResponse>>,
                        t: Throwable
                    ) {
                        Log.e(TAG, t.message!!)
                        _result.value = Result(false, t.message!!)
                    }

                    override fun onResponse(
                        call: Call<BaseResponse<AuthResponse>>,
                        response: Response<BaseResponse<AuthResponse>>
                    ) {
                        response.body()?.apply {
                            if (status) {
                                Log.d(TAG, msg)
                                _result.value =
                                    Result(true, msg)
                            } else {
                                Log.d(TAG, msg)
                                _result.value =
                                    Result(false, msg)
                            }
                        }
                    }
                })
        } else {
            Log.d(TAG, validity.message)
            _result.value = validity
        }
    }

    private fun checkForValidity(email: String, password: String, confirmPassword: String): Result {
        if (TextUtils.isEmpty(email)) {
            return Result(false, "Email must not be empty")
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Result(false, "Email is invalid")
        }

        if (TextUtils.isEmpty(password)) {
            return Result(false, "Password must not be empty")
        } else if (password.length < 6) {
            return Result(false, "Password is short")
        } else if (TextUtils.isEmpty(confirmPassword)) {
            return Result(false, "Confirm Password must not be empty")
        } else if (confirmPassword.length < 6) {
            return Result(false, "Confirm Password is short")
        } else if (password != confirmPassword) {
            return Result(false, "Password and confirm password must same")
        }
        return Result(true, "Success")
    }

    override fun onCleared() {
        super.onCleared()
        Log.d(TAG, "onCleared: ")
    }
}
