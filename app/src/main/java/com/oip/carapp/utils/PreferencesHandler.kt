package com.oip.carapp.utils

import android.content.Context
import com.oip.carapp.CarApplication
import com.oip.carapp.utils.Constants.IS_LOGIN
import com.oip.carapp.utils.Constants.PREF_NAME
import com.oip.carapp.utils.Constants.PROFILE_IMAGE_URL
import com.oip.carapp.utils.Constants.TOKEN
import com.oip.carapp.utils.Constants.USER_ID

object PreferencesHandler {

    private val preferences =
        CarApplication.getCtx().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun setIsLogin(isLogin: Boolean) {
        editor.putBoolean(IS_LOGIN, isLogin)
        editor.apply()
    }

    fun getIsLogin() = preferences.getBoolean(IS_LOGIN, false)

    fun setUserId(userId: String) {
        editor.putString(USER_ID, userId)
        editor.apply()
    }

    fun getUserId() = preferences.getString(USER_ID, "")

    fun setProfileImageUrl(imageUrl: String) {
        editor.putString(PROFILE_IMAGE_URL, imageUrl)
        editor.apply()
    }

    fun getProfileImageUrl() = preferences.getString(PROFILE_IMAGE_URL, "")

    fun setToken(token: String) {
        editor.putString(TOKEN, token)
        editor.apply()
    }

    fun getToken() = preferences.getString(TOKEN, "")
}