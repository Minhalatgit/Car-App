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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpViewModel : ViewModel() {

    private val TAG = "SignUpViewModel"

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result>
        get() = _result

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        Log.d(TAG, "SignUp view model initialized")
    }

    fun register(email: String, password: String, confirmPassword: String) {
        val validity = checkForValidity(email, password, confirmPassword)
        if (validity.isValid) {
            Log.d(TAG, validity.message)
            coroutineScope.launch {
                try {
                    val registerResult =
                        RetrofitClient.apiInterface.register(email, password, confirmPassword)
                    registerResult.apply {
                        if (status) {
                            Log.d(TAG, msg)
                            _result.value = Result(true, msg)
                        } else {
                            Log.d(TAG, msg)
                            _result.value = Result(false, msg)
                        }
                    }
                } catch (t: Throwable) {
                    Log.e(TAG, t.message!!)
                    _result.value = Result(false, t.message!!)
                }
            }
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
            return Result(false, "Password and confirm password must be same")
        }
        return Result(true, "Success")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.d(TAG, "onCleared: ")
    }
}
