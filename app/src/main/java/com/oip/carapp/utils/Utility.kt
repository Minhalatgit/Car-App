package com.oip.carapp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.oip.carapp.R
import com.oip.carapp.SplashActivity

fun createNotificationChannel(context: Context) {
    // Create the NotificationChannel, but only on API 26+ because
    // the NotificationChannel class is new and not in the support library
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("CHANNEL_1", "CHANNEL_NAME", importance).apply {
            description = "Description"
        }
        // Register the channel with the system
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

fun showNotification(context: Context, title: String, content: String) {
    val intent = Intent(context, SplashActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }
    val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

    val builder = NotificationCompat.Builder(context, "CHANNEL_1")
        .setSmallIcon(R.drawable.notification_icon)
        .setContentTitle(title)
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        // Set the intent that will fire when the user taps the notification
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    with(NotificationManagerCompat.from(context)) {
        // notificationId is a unique int for each notification that you must define
        notify(1, builder.build())
    }
}

fun subscribeToAllTopic() {
    Firebase.messaging.subscribeToTopic("all").addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.e("FirebaseSubscribe", "Failed" + task.exception?.message!!)
        }
        Log.d("FirebaseSubscribe", "Success")
    }
}

fun unsubscribeToAllTopic() {
    Firebase.messaging.unsubscribeFromTopic("all").addOnCompleteListener { task ->
        if (!task.isSuccessful) {
            Log.e("FirebaseUnSubscribe", "Failed" + task.exception?.message!!)
        }
        Log.d("FirebaseUnSubscribe", "Success")
    }
}

