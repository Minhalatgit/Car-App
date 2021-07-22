package com.oip.carapp.home.views

import android.content.Intent
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
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceBookingBinding
import com.oip.carapp.home.models.ServiceResponse
import com.oip.carapp.home.viewmodel.ServiceListViewModel
import com.oip.carapp.utils.*
import com.oip.carapp.utils.Constants.BASE_URL_IMAGES
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.android.synthetic.main.fragment_service_booking.*
import java.util.*

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
        viewModel.bookingResult.observe(viewLifecycleOwner, Observer {
            hideProgressBar(window, binding.progress)
            if (it.isValid) {
                //Booking success
                Navigation.findNavController(requireActivity(), R.id.navHostFragment)
                    .navigate(ServiceBookingFragmentDirections.actionServiceBookingFragmentToHomeFragment())
            }
            requireActivity().toast(it.message)
        })

        binding.bookService.setOnClickListener {

            if (binding.bookingDate.text == "Select date" || binding.bookingTime.text == "Select time") {
                return@setOnClickListener
            }

            val bookingDateTime = getDate(
                "${binding.bookingDate.text} ${binding.bookingTime.text}",
                SERVICE_BOOKING_DATE_TIME_FORMAT, SERVER_DATE_FORMAT
            )
            Log.d(TAG, bookingDateTime)
            showProgressBar(window, binding.progress)

            viewModel.bookService(serviceData.id, bookingDateTime)
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

        binding.bookingDate.transformIntoDatePicker(
            requireContext(),
            APPOINTMENT_DATE_FORMAT,
            Date()
        )
        binding.bookingTime.transformIntoTimePicker(requireContext())

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
