package com.oip.carapp.home.views

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceBookingBinding
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.home.viewmodel.ServiceBookingViewModel
import com.oip.carapp.utils.Constants.BASE_URL_IMAGES
import com.oip.carapp.utils.PreferencesHandler
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class ServiceBookingFragment : BaseFragment() {

    companion object {
        private const val TAG = "ServiceBookingFragment"
    }

    private lateinit var binding: FragmentServiceBookingBinding
    private lateinit var args: ServiceBookingFragmentArgs
    private lateinit var serviceData: ServiceResponse
    private lateinit var viewModel: ServiceBookingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBookingBinding.inflate(layoutInflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)
        args = ServiceBookingFragmentArgs.fromBundle(requireArguments())

        serviceData = args.serviceData

        Log.d(TAG, "Service data: $serviceData")

        setServiceData()

        viewModel = ViewModelProvider(this).get(ServiceBookingViewModel::class.java)

        binding.bookService.setOnClickListener {
            //viewModel.bookService("")
        }

        binding.back.setOnClickListener {

        }

        return binding.root
    }

    private fun setServiceData() {
        binding.off.text = PreferencesHandler.getOffer() + "% OFF"
        serviceData.apply {
            Picasso.get().load(BASE_URL_IMAGES + serviceImage)
                .placeholder(R.drawable.booking)
                .into(object : Target {
                    override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                        binding.mainLayout.background =
                            BitmapDrawable(requireContext().resources, bitmap)
                    }

                    override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                    }

                    override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                    }

                })

            if (isFavourite == "1") {
                binding.favourite.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.red)
                )
            } else {
                binding.favourite.setColorFilter(
                    ContextCompat.getColor(requireContext(), R.color.grey)
                )
            }
            binding.serviceTitle.text = serviceTitle
            binding.subtitle.text = serviceSubtitle
            binding.amount.text = "$$serviceAmount"
            binding.distance.text = "$serviceDistance Km"
        }
    }
}
