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

class VerifyCodeViewModel : ViewModel() {

    private val TAG = "VerifyCodeViewModel"

    private val _result = MutableLiveData<Result>()
    val result: LiveData<Result>
        get() = _result

    init {
        Log.d(TAG, "VerifyCodeViewModel initialized")
    }

    fun verify(email: String, code: String) {
        val validity = checkForValidity(code)
        if (validity.isValid) {
            Log.d(TAG, validity.message)
            RetrofitClient.apiInterface.verifyCode(email, code)
                .enqueue(object : Callback<BaseResponse<AuthResponse>> {
                    override fun onFailure(call: Call<BaseResponse<AuthResponse>>, t: Throwable) {
                        Log.e(TAG, t.message!!)
                        _result.value = Result(false, t.message!!)
                    }

                    override fun onResponse(
                        call: Call<BaseResponse<AuthResponse>>,
                        response: Response<BaseResponse<AuthResponse>>
                    ) {
                        response.body()?.apply {
                            if (status) {
                                if (data.status == 1){
                                    Log.d(TAG, msg)
                                    _result.value =
                                        Result(true, msg)
                                    PreferencesHandler.apply {
                                        setUsername(data.name)
                                        setProfileImageUrl(data.image)
                                        //setToken(data.token)
                                        setUserId(data.id)
                                        setIsLogin(true)
                                    }
                                }
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

    private fun checkForValidity(code: String): Result {
        if (TextUtils.isEmpty(code)) {
            return Result(false, "Code must not be empty")
        } else if (code.length < 4) {
            return Result(false, "Code is incomplete")
        }
        return Result(true, "Success")
    }
}