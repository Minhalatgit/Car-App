package com.oip.carapp.notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.oip.carapp.utils.showNotification

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        Log.d("FirebaseMessage", remoteMessage.notification?.title ?: "Empty title")

        val title = remoteMessage.notification?.title ?: "Empty title"
        val content = remoteMessage.notification?.body ?: "Empty body"

        showNotification(this, title!!, content!!)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("FirebaseToken", token)
    }
}