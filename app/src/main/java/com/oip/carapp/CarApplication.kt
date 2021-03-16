package com.oip.carapp

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class CarApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        instance = this

        try {
            val info = packageManager.getPackageInfo(
                "com.oip.carapp",
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md: MessageDigest = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            Log.d("KeyHash:", e.toString())
        } catch (e: NoSuchAlgorithmException) {
            Log.d("KeyHash:", e.toString())
        }

    }

    companion object {
        lateinit var instance: CarApplication
        fun getCtx(): Context {
            return instance.applicationContext
        }
    }
}