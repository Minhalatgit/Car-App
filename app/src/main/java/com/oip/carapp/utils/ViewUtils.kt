package com.oip.carapp.utils

import android.app.DatePickerDialog
import android.content.Context
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.github.ybq.android.spinkit.SpinKitView
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun showProgressBar(window: Window, progress: ProgressBar) {
    progress.visibility = View.VISIBLE
//    window.setFlags(
//        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//    )
}

fun hideProgressBar(window: Window, progress: ProgressBar) {
    progress.visibility = View.GONE
//    window.clearFlags(
//        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
//    )
}

fun TextView.transformIntoDatePicker(context: Context, format: String, maxDate: Date? = null) {
    val myCalendar = Calendar.getInstance()
    val datePickerOnDataSetListener =
        DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val sdf = SimpleDateFormat(format, Locale.UK)
            text = sdf.format(myCalendar.time)
        }

    setOnClickListener {
        DatePickerDialog(
            context, datePickerOnDataSetListener, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}