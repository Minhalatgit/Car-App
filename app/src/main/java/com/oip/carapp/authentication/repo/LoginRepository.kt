package com.oip.carapp.authentication.repo

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object LoginRepository {

    val loginResponse = MutableLiveData<BaseResponse<AuthResponse>>()

    fun login(email: String, password: String): MutableLiveData<BaseResponse<AuthResponse>> {
        RetrofitClient.apiInterface.login(email, password)
            .enqueue(object : Callback<BaseResponse<AuthResponse>> {
                override fun onFailure(call: Call<BaseResponse<AuthResponse>>, t: Throwable) {
                    Log.e("LoginRepository", "onFailure: ${t.message}")
                }

                override fun onResponse(
                    call: Call<BaseResponse<AuthResponse>>,
                    response: Response<BaseResponse<AuthResponse>>
                ) {
                    Log.d("LoginRepository", "Response ${response.body()}")
                    loginResponse.value = response.body()
                }

            })

        return loginResponse
    }

}