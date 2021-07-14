package com.oip.carapp.home.views

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
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentStoreBinding
import com.oip.carapp.home.viewmodel.StoreViewModel
import java.lang.Exception

class StoreFragment : BaseFragment() {

    private lateinit var binding: FragmentStoreBinding

    private lateinit var googleMap: GoogleMap
    private lateinit var supportFragment: SupportMapFragment

    private lateinit var viewModel: StoreViewModel

    private val TAG = "MapFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            FragmentStoreBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(StoreViewModel::class.java)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        supportFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        supportFragment.getMapAsync {
            Log.d(TAG, "Map is ready")
            googleMap = it
            viewModel.getStores()
        }

        viewModel.storeList.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: $it")

            if (it.isNotEmpty()) {
                googleMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        LatLng(
                            it[0].storeLatitude,
                            it[0].storeLongitude
                        ), 8f
                    )
                )
            }

            for (item in it) {

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
                    .position(LatLng(item.storeLatitude, item.storeLongitude))
                    .title(item.storeAddress)

                googleMap.addMarker(options)
            }
        })
    }
}