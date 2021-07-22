package com.oip.carapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
const val SERVER_BIRTHDAY_DATE_FORMAT = "yyyy-MM-dd"
const val BIRTHDAY_DATE_FORMAT = "EEE, dd MMM yyyy"
const val APPOINTMENT_DATE_FORMAT = "dd-MM-yyyy"
const val APPOINTMENT_TIME_FORMAT = "hh:mm:ss a"
const val UPCOMING_APPOINTMENT_TIME_FORMAT = "dd/MM/yyyy"
const val SERVICE_BOOKING_DATE_TIME_FORMAT = "dd-MM-yyyy hh:mm"

fun getDate(date: String, formatIn: String, formatOut: String): String {
    val spdIn = SimpleDateFormat(formatIn)
    val sdpOut = SimpleDateFormat(formatOut)
    var formattedDate: Date? = null
    try {
        formattedDate = spdIn.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return if (formattedDate == null) {
        date
    } else {
        sdpOut.format(formattedDate)
    }
}

