package com.oip.carapp.home.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentStoreBinding
import com.oip.carapp.home.viewmodel.StoreViewModel
import com.oip.carapp.utils.toast
import java.lang.Exception

class StoreFragment : BaseFragment() {

    private lateinit var binding: FragmentStoreBinding

    private lateinit var googleMap: GoogleMap
    private lateinit var supportFragment: SupportMapFragment

    private lateinit var viewModel: StoreViewModel

    private val TAG = "MapFragment"

    @SuppressLint("MissingPermission")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentStoreBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        supportFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportFragment.getMapAsync {
            Log.d(TAG, "Map is ready")
            googleMap = it
            viewModel.getStores()
        }

        viewModel.storeList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: $it")

            val latLngList = mutableListOf<LatLng>()

            if (it.isNotEmpty()) {
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it[0].storeLatitude,
                            it[0].storeLongitude
                        ), 5f
                    )
                )
            }

            for (item in it) {
                latLngList.add(LatLng(item.storeLatitude, item.storeLongitude))

                var address: String? = null
                val addresses = Geocoder(requireContext())
                try {
                    address =
                        addresses.getFromLocation(
                            item.storeLatitude,
                            item.storeLongitude,
                            1
                        )[0].getAddressLine(0)
                } catch (e: Exception) {
                    Log.e(TAG, e.message ?: "Exception")
                }

                val options = MarkerOptions()
                    //.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker_icon))
                    .position(LatLng(item.storeLatitude, item.storeLongitude))
                    .title(address ?: item.storeAddress)

                googleMap.addMarker(options)
            }

            googleMap.addPolyline(PolylineOptions().apply {
                addAll(latLngList)
                width(15f)
                color(Color.RED)
                geodesic(true)
                clickable(true)
            })

            googleMap.setOnPolylineClickListener {
                requireContext().toast("Polyline")
            }
        })

        return binding.root
    }
}