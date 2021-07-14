package com.oip.carapp.authentication.viewmodel

import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oip.carapp.BaseViewModel
import com.oip.carapp.authentication.model.Result
import com.oip.carapp.retrofit.RetrofitClient
import com.oip.carapp.utils.PreferencesHandler
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {

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
            coroutineScope.launch {
                try {
                    val loginResult = RetrofitClient.apiInterface.login(email, password)
                    loginResult.apply {
                        if (status) {
                            if (data.status == 1) {
                                //verified account
                                Log.d(TAG, "Account verified")
                            } else {
                                //Not verified account
                                Log.d(TAG, "Not Verified")
                            }
                            _result.value = Result(true, msg)
                            Log.d(TAG, msg)
                            PreferencesHandler.apply {
                                setUsername(data.name)
                                setProfileImageUrl(data.image)
                                //setToken(data.token)
                                setUserId(data.id)
                                setIsLogin(true)
                            }
                        } else {
                            // server response
                            _result.value = Result(false, msg)
                            Log.d(TAG, msg)
                        }
                    }

                } catch (t: Throwable) {
                    // server error
                    _result.value = Result(false, t.message!!)
                    Log.e(TAG, t.message!!)
                }
            }
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
            return Result(false, "Password is too short")
        }
        return Result(true, "Success")
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
        Log.d(TAG, "onCleared: ")
    }
}
