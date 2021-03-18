package com.oip.carapp.utils

import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.github.ybq.android.spinkit.SpinKitView

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun showProgressBar(window: Window, progress: ProgressBar) {
    progress.visibility = View.VISIBLE
    window.setFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}

fun hideProgressBar(window: Window, progress: ProgressBar) {
    progress.visibility = View.GONE
    window.clearFlags(
        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
    )
}