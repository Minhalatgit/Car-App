package com.oip.carapp.utils

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import com.vmadalin.easypermissions.EasyPermissions

fun Context.isLocationPermissionGranted(): Boolean {
    return EasyPermissions.hasPermissions(this, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION)
}