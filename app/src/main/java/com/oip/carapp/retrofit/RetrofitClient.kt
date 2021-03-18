package com.oip.carapp.retrofit

import com.oip.carapp.BuildConfig
import com.oip.carapp.utils.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofitClient: Retrofit.Builder by lazy {

        val levelType: Level = if (BuildConfig.BUILD_TYPE.contentEquals("debug"))
            Level.BODY else Level.NONE
        val logging = HttpLoggingInterceptor()
        logging.setLevel(levelType)
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiInterface: ApiInterface by lazy {
        retrofitClient
            .build()
            .create(ApiInterface::class.java)
    }
}