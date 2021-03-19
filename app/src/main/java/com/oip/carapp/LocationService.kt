package com.oip.carapp

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.oip.carapp.utils.Constants.ACTION_START_LOCATION_SERVICE
import com.oip.carapp.utils.Constants.ACTION_STOP_LOCATION_SERVICE
import com.oip.carapp.utils.Constants.LOCATION_SERVICE_ID
import java.lang.UnsupportedOperationException

class LocationService : Service() {

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            if (locationResult != null && locationResult.lastLocation != null) {
                locationResult.apply {
                    val lat = lastLocation.latitude
                    val long = lastLocation.longitude
                    Log.d("LocationService", "Lat: $lat Long: $long")
                }
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        throw UnsupportedOperationException("Not implemented")
    }

    private fun startLocationService() {
        val channelId = "location_notification_id"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val resultIntent = Intent()
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(applicationContext, channelId)
        builder.apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setContentTitle("Location service")
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setContentText("Running")
            setContentIntent(pendingIntent)
            setAutoCancel(false)
            priority = NotificationCompat.PRIORITY_HIGH
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelId) == null) {
                val channel = NotificationChannel(
                    channelId,
                    "Location Service",
                    NotificationManager.IMPORTANCE_HIGH
                )
                channel.description = "This channel is used by location service"
                notificationManager.createNotificationChannel(channel)
            }
        }

        val locationRequest = LocationRequest()
        locationRequest.apply {
            interval = 4000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
        startForeground(LOCATION_SERVICE_ID, builder.build())
    }

    private fun stopLocationService() {
        LocationServices.getFusedLocationProviderClient(this)
            .removeLocationUpdates(locationCallback)
        stopForeground(true)
        stopSelf()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null) {
            val action = intent.action
            if (action != null) {
                if (action == ACTION_START_LOCATION_SERVICE) {
                    startLocationService()
                } else if (action == ACTION_STOP_LOCATION_SERVICE) {
                    stopLocationService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }
}