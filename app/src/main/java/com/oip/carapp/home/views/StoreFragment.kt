package com.oip.carapp.home.views

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentStoreBinding
import java.lang.Exception

class StoreFragment : BaseFragment() {

    private lateinit var binding: FragmentStoreBinding
    private lateinit var servicesRecyclerView: RecyclerView

    private val REQUEST_LOCATION = 1001
    private lateinit var googleMap: GoogleMap
    private lateinit var supportFragment: SupportMapFragment
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest
    lateinit var locationCallback: LocationCallback
    lateinit var marker: Marker

    private val TAG = "MapFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentStoreBinding.inflate(inflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)
//        locationRequest = LocationRequest.create().apply {
//            interval = 1000 * 5
//            fastestInterval = 1000 * 2
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }
//        locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult) {
//                super.onLocationResult(locationResult)
//                Log.d(TAG, "Requesting location")
//                getLocationData(locationResult.lastLocation)
//            }
//        }

//        updateGps()

        supportFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        return binding.root
    }

    private fun moveCamera(
        latLng: LatLng,
        zoom: Float,
        title: String
    ) {
        var address = "Address"
        val addresses = Geocoder(requireContext())
        try {
            address =
                addresses.getFromLocation(latLng.latitude, latLng.longitude, 1)[0].getAddressLine(0)
        } catch (e: Exception) {
            Log.e(TAG, "moveCamera: ${e.message}")
        }

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        val options = MarkerOptions()
            .position(latLng)
            .title(address)

        if (this::marker.isInitialized)
            marker.remove()

        marker = googleMap.addMarker(options)
    }

    private fun updateGps() {
        enableGps()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                val location = it
                if (it != null) {
                    supportFragment.getMapAsync {
                        Log.d(TAG, "Map is ready")
                        googleMap = it
                        getLocationData(location)
                    }
                } else {
                    Log.d(TAG, "Location is null")
                }
            }
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                null
            )
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(arrayOf(ACCESS_FINE_LOCATION), REQUEST_LOCATION)
            }
        }
    }

    private fun getLocationData(location: Location) {
        if (this::googleMap.isInitialized)
            moveCamera(LatLng(location.latitude, location.longitude), 15f, "My current location")
        location.apply {
            Log.d(TAG, "Long:$longitude Lat:$latitude Accuracy $accuracy")
        }
    }

    private fun isGpsEnabled(): Boolean {
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    private fun enableGps() {
        if (!isGpsEnabled()) {
            val gpsBuilder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
            gpsBuilder.setAlwaysShow(true)
            val task =
                LocationServices.getSettingsClient(requireContext())
                    .checkLocationSettings(gpsBuilder.build())
            task.addOnCompleteListener {
                try {
                    val response = task.getResult(ApiException::class.java)
                    // All location settings are satisfied. The client can initialize location
                    // requests here.
                } catch (exception: ApiException) {
                    when (exception.statusCode) {
                        LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                            // Location settings are not satisfied. But could be fixed by showing the
                            // user a dialog.
                            try {
                                // Cast to a resolvable exception.
                                val resolvable = exception as ResolvableApiException
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                resolvable.startResolutionForResult(requireActivity(), 100)
                            } catch (e: IntentSender.SendIntentException) {
                                // Ignore the error.
                            } catch (e: ClassCastException) {
                                // Ignore, should be an impossible error.
                            }
                        }
                        LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE ->
                            // Location settings are not satisfied. However, we have no way to fix the
                            // settings so we won't show the dialog.
                            Log.d(TAG, "Cannot fix settings issue")
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            REQUEST_LOCATION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    updateGps()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "This app requires permission",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}