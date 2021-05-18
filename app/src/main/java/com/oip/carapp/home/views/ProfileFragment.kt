package com.oip.carapp.home.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.databinding.FragmentProfileBinding
import com.oip.carapp.home.viewmodel.ProfileViewModel
import com.oip.carapp.utils.*
import com.squareup.picasso.Picasso
import java.io.File

class ProfileFragment : BaseFragment() {

    private val TAG = "ProfileFragment"

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    private var profileData: AuthResponse? = null
    private var fileUri: Uri? = null

    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        window.statusBarColor = requireActivity().getColor(R.color.yellow)

        binding.edit.setOnClickListener {
            editModeOn()
        }

        binding.cancel.setOnClickListener {
            editModeOff()
            if (profileData != null)
                setProfileData(profileData!!)
        }

        binding.done.setOnClickListener {
            editModeOff()
            showProgressBar(window, binding.progress)
            viewModel.updateProfile(
                binding.usernameTwo.text.toString(),
                binding.phone.text.toString(),
                binding.gender.text.toString(),
                binding.birthday.text.toString(),
                PreferencesHandler.getUserId()!!,
                File(fileUri?.path!!)
            )
        }

        binding.uploadIcon.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024)            //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()
        }

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.getProfile()

        viewModel.profileData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: $it")
            profileData = it
            setProfileData(it)
        })
        viewModel.updatedProfileData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "Updated profile data: $it")
            profileData = it
            hideProgressBar(window, binding.progress)
            setProfileData(it)
        })

        return binding.root
    }

    private fun editModeOn() {
        isEditMode = true
        binding.uploadIcon.visibility = View.VISIBLE
        binding.done.visibility = View.VISIBLE
        binding.cancel.visibility = View.VISIBLE
        binding.edit.visibility = View.GONE
        binding.profileImage.alpha = 0.5f
    }

    private fun editModeOff() {
        isEditMode = false
        binding.uploadIcon.visibility = View.GONE
        binding.done.visibility = View.GONE
        binding.cancel.visibility = View.GONE
        binding.edit.visibility = View.VISIBLE
        binding.profileImage.alpha = 1.0f
    }

    private fun setProfileData(profileData: AuthResponse) {
        binding.apply {
            username.text = profileData.name
            usernameTwo.setText(profileData.name)
            phone.setText(profileData.phone)
            email.text = profileData.email
            gender.setText(profileData.gender)
            birthday.setText(profileData.birthday)
            Picasso.get().load(Constants.BASE_URL_IMAGES + PreferencesHandler.getProfileImageUrl())
                .placeholder(R.drawable.profile_placeholder).into(profileImage)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            RESULT_OK -> {
                //Image Uri will not be null for RESULT_OK
                val uri: Uri = data?.data!!

                fileUri = uri

                // Use Uri object instead of File to avoid storage permissions
                binding.profileImage.setImageURI(uri)
            }
            ImagePicker.RESULT_ERROR -> {
                Log.e(TAG, "onActivityResult: ${ImagePicker.getError(data)}")
            }
            else -> {
                Log.d(TAG, "onActivityResult: Task Cancelled")
            }
        }
    }
}