package com.oip.carapp.home.views

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceBookingBinding
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.home.viewmodel.ServiceListViewModel
import com.oip.carapp.utils.Constants.BASE_URL_IMAGES
import com.oip.carapp.utils.PreferencesHandler
import com.oip.carapp.utils.toast
import com.oip.carapp.utils.updateFavouriteIconColor
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class ServiceBookingFragment : BaseFragment() {

    companion object {
        private const val TAG = "ServiceBookingFragment"
    }

    private lateinit var binding: FragmentServiceBookingBinding
    private lateinit var args: ServiceBookingFragmentArgs
    private lateinit var serviceData: ServiceResponse
    private lateinit var viewModel: ServiceListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBookingBinding.inflate(layoutInflater, container, false)
        args = ServiceBookingFragmentArgs.fromBundle(requireArguments())

        serviceData = args.serviceData

        Log.d(TAG, "Service data: $serviceData")

        setServiceData()

        viewModel = ViewModelProvider(this).get(ServiceListViewModel::class.java)

        viewModel.favouriteUpdated.observe(viewLifecycleOwner, Observer {
            if (it) {
                serviceData.isFavourite = "1"
                binding.favourite.updateFavouriteIconColor("1", requireContext())
            } else {
                serviceData.isFavourite = "0"
                binding.favourite.updateFavouriteIconColor("0", requireContext())
            }
        })

        binding.bookService.setOnClickListener {
            //viewModel.bookService("")
            requireContext().toast("Book service")
        }

        binding.favourite.setOnClickListener {
            if (serviceData.isFavourite == "1") {
                viewModel.updateFavourite(serviceData.id, "0")
            } else {
                viewModel.updateFavourite(serviceData.id, "1")
            }
        }

        binding.back.setOnClickListener {
            it.findNavController().popBackStack()
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

            binding.favourite.updateFavouriteIconColor(isFavourite, requireContext())

            binding.serviceTitle.text = serviceTitle
            binding.subtitle.text = serviceSubtitle
            binding.amount.text = "$$serviceAmount"
            binding.distance.text = "$serviceDistance Km"
        }
    }
}
