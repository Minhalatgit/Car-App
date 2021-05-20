package com.oip.carapp.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getDate(date: String): String {
    val formatIn = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    val formatOut = SimpleDateFormat("EEE, dd MMM yyyy")
    var formattedDate: Date? = null
    try {
        formattedDate = formatIn.parse(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return if (formattedDate == null) {
        date
    } else {
        formatOut.format(formattedDate)
    }
}
