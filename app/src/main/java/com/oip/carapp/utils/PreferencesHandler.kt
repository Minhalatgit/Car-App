package com.oip.carapp.utils

import android.content.Context
import com.oip.carapp.CarApplication
import com.oip.carapp.utils.Constants.IS_LOGIN
import com.oip.carapp.utils.Constants.PREF_NAME

object PreferencesHandler {

    private val preferences =
        CarApplication.getCtx().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun setIsLogin(isLogin: Boolean) {
        editor.putBoolean(IS_LOGIN, isLogin)
        editor.apply()
    }

    fun getIsLogin() = preferences.getBoolean(IS_LOGIN, false)

}