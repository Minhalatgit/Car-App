package com.oip.carapp.utils

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.core.content.ContextCompat
import com.oip.carapp.R
import com.oip.carapp.retrofit.RetrofitClient
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

fun Context.toast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Activity.hideKeyboard(): Boolean {
    return (getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
        .hideSoftInputFromWindow((currentFocus ?: View(this)).windowToken, 0)
}

fun Activity.updateProfilePicture() {
    Picasso.Builder(this)
        .downloader(OkHttp3Downloader(RetrofitClient.unSafeOkHttpClient().build()))
        .build()
        .load(Constants.BASE_URL_IMAGES + PreferencesHandler.getProfileImageUrl())
        .placeholder(R.drawable.profile_placeholder)
        .into(this.profileImage)
}

fun Context.loadImage(imageView: ImageView, imagePath: String) {
    Picasso.Builder(this)
        .downloader(OkHttp3Downloader(RetrofitClient.unSafeOkHttpClient().build()))
        .build()
        .load(Constants.BASE_URL_IMAGES + imagePath)
        .placeholder(R.drawable.profile_placeholder)
        .error(R.drawable.profile_placeholder)
        .into(imageView)
}

fun ImageView.updateFavouriteIconColor(isFavourite: String, context: Context) {
    if (isFavourite == "1") {
        this.setColorFilter(
            ContextCompat.getColor(context, R.color.red)
        )
    } else {
        this.setColorFilter(
            ContextCompat.getColor(context, R.color.grey)
        )
    }
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
            context, R.style.DialogTheme,
            datePickerOnDataSetListener,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ).run {
            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}

fun TextView.transformIntoTimePicker(context: Context) {
    val currentTime = Calendar.getInstance()
    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)

    val timePickerListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
        Log.d(
            "TIME",
            "Selected hour: $selectedHour and Selected minute: $selectedMinute"
        )
        text = "$selectedHour:$selectedMinute"
//        text = if (selectedHour < 12) {
//            "$selectedHour:$selectedMinute AM"
//        } else {
//            "$selectedHour:$selectedMinute PM"
//        }
    }

    setOnClickListener {
        TimePickerDialog(
            context, R.style.DialogTheme,
            timePickerListener,
            hour, minute, true
        ).run {
//            maxDate?.time?.also { datePicker.maxDate = it }
            show()
        }
    }
}

fun showTimePickerDialog(context: Context): TimePickerDialog {
    val currentTime = Calendar.getInstance()
    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)
    val timePickerDialog = TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            Log.d(
                "TIME",
                "Selected hour: $selectedHour and Selected minute: $selectedMinute"
            )
        }, hour, minute, true
    )
    timePickerDialog.setTitle("Select Time")
    timePickerDialog.show()
    return timePickerDialog
}
