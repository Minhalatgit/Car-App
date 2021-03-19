package com.oip.carapp.home.views

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.oip.carapp.LocationService
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentMapBinding
import com.oip.carapp.home.adapters.ServiceAdapter
import com.oip.carapp.home.models.Service
import com.oip.carapp.utils.Constants.ACTION_START_LOCATION_SERVICE
import com.oip.carapp.utils.Constants.ACTION_STOP_LOCATION_SERVICE
import com.oip.carapp.utils.toast
import com.suke.widget.SwitchButton

class MapFragment : Fragment(), ServiceAdapter.ServiceListener {

    private lateinit var binding: FragmentMapBinding
    private lateinit var servicesRecyclerView: RecyclerView
    private val list = ArrayList<Service>()

    lateinit var toolbar: Toolbar
    lateinit var title: TextView
    lateinit var switch: SwitchButton
    lateinit var navigationIcon: ImageView

    private val REQUEST_LOCATION = 1001
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private lateinit var supportFragment: SupportMapFragment

    private var isLocationPermission = false

    private val TAG = "MapFragment"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        list.add(
            Service(
                "$ 25.00",
                "22 km",
                "Jump",
                "Lorem ipsum dolor"
            )
        )
        list.add(
            Service(
                "$ 25.00",
                "22 km",
                "Tow",
                "Lorem ipsum dolor"
            )
        )
        list.add(
            Service(
                "$ 25.00",
                "22 km",
                "Lockout",
                "Lorem ipsum dolor"
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            FragmentMapBinding.inflate(inflater, container, false)
        initViews()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        // Check location permission
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is already granted
            isLocationPermission = true
            getDeviceLocation()
        } else {
            // Permission not granted, now asking..
            isLocationPermission = false
            requestPermissions(arrayOf(ACCESS_FINE_LOCATION), REQUEST_LOCATION)
        }
        supportFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment

        servicesRecyclerView.adapter =
            ServiceAdapter(
                list,
                requireContext(),
                this
            )

        switch.setOnCheckedChangeListener { view, isChecked ->
            if (isChecked) {
                binding.statusLayout.visibility = View.VISIBLE
            } else {
                binding.statusLayout.visibility = View.GONE
            }
        }

        return binding.root
    }

    override fun onServiceClick(position: Int) {
        // Sending service name to next Service fragment
        Navigation.findNavController(requireActivity(), R.id.navHostFragment)
            .navigate(
                MapFragmentDirections.actionMapFragmentToServiceFragment(
                    list[position].serviceName
                )
            )
    }

    private fun initViews() {
        toolbar = activity?.findViewById(R.id.toolbar)!!
        title = toolbar.findViewById(R.id.title)
        switch = toolbar.findViewById(R.id.switch_button)
        navigationIcon = toolbar.findViewById(R.id.navigationIcon)

        navigationIcon.visibility = View.VISIBLE
        switch.visibility = View.VISIBLE
        title.text = "" // no title for map fragment
        requireActivity().window.statusBarColor =
            ContextCompat.getColor(requireContext(), R.color.white)

        servicesRecyclerView = binding.serviceRecyclerView
        servicesRecyclerView.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun moveCamera(
        latLng: LatLng,
        zoom: Float,
        title: String
    ) {
        Log.d(
            TAG,
            "moveCamera: moving the camera to: lat: ${latLng.latitude}, long: ${latLng.longitude}"
        )
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom))
        val options = MarkerOptions()
            .position(latLng)
            .title(title)
        googleMap.addMarker(options)
    }

    // Getting device location and initializing map with current location
    @SuppressLint("MissingPermission")
    private fun getDeviceLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.apply {
                Log.d(TAG, "Current location: Lat: $latitude Long: $longitude")
//                supportFragment.getMapAsync {
//                    requireContext().toast("Map is ready to use")
//                    it.isMyLocationEnabled = true
//                    it.uiSettings.isMyLocationButtonEnabled = true
//                    googleMap = it
//                    moveCamera(LatLng(latitude, longitude), 15f, "My current location")
//                }
            }
        }
    }

    private fun enableLocationSettings() {
        val locationRequest = LocationRequest.create()
            .setInterval(10000)
            .setFastestInterval(5000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client = LocationServices.getSettingsClient(requireActivity())
        val task = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener {
            requireContext().toast("Location setting success")
        }
        task.addOnFailureListener {
            requireContext().toast("Location setting failed")
            if (it is ResolvableApiException) {
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    it.startResolutionForResult(
                        requireActivity(),
                        1
                    )
                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
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
        if (requestCode == REQUEST_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                isLocationPermission = true
                getDeviceLocation()
            }
        }
    }

    private fun isLocationServiceRunning(): Boolean {
        val activityManager =
            requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        if (activityManager != null) {
            for (service in activityManager.getRunningServices(Int.MAX_VALUE)) {
                if (LocationService::class.java.name == service.service.className) {
                    if (service.foreground) {
                        return true
                    }
                }
            }
            return false
        }
        return false
    }

    private fun startLocationService() {
        if (!isLocationServiceRunning()) {
            val intent = Intent(requireContext().applicationContext, LocationService::class.java)
            intent.action = ACTION_START_LOCATION_SERVICE
            requireActivity().startService(intent)
            requireContext().toast("Location service started")
        }
    }

    private fun stopLocationService() {
        if (isLocationServiceRunning()) {
            val intent = Intent(requireContext().applicationContext, LocationService::class.java)
            intent.action = ACTION_STOP_LOCATION_SERVICE
            requireActivity().startService(intent)
            requireContext().toast("Location service stopped")
        }
    }
}