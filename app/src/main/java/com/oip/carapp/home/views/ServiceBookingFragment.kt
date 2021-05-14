package com.oip.carapp.home.views

import android.R.attr
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentServiceBookingBinding
import com.oip.carapp.utils.Constants.BASE_URL_IMAGES
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target


class ServiceBookingFragment : BaseFragment() {

    private lateinit var binding: FragmentServiceBookingBinding
    lateinit var args: ServiceBookingFragmentArgs

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentServiceBookingBinding.inflate(layoutInflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.white)
        args = ServiceBookingFragmentArgs.fromBundle(requireArguments())

        Picasso.get().load(BASE_URL_IMAGES + args.serviceImage).placeholder(R.drawable.booking)
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

        binding.back.setOnClickListener {

        }

        return binding.root
    }

    //below code is to make screen full screen
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }

    override fun onDetach() {
        super.onDetach()
//        window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
    }
}