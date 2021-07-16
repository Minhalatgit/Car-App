package com.oip.carapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.oip.carapp.authentication.views.LoginActivity
import com.oip.carapp.home.MainActivity
import com.oip.carapp.utils.PreferencesHandler
import com.oip.carapp.utils.createNotificationChannel

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        createNotificationChannel(this)

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = if (PreferencesHandler.getIsLogin()) {
                Intent(this, MainActivity::class.java)
            } else {
                Intent(this, LoginActivity::class.java)
            }
            startActivity(intent)
            finish()
        }, 2000)
    }
}