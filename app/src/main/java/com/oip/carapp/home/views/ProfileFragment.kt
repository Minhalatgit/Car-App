package com.oip.carapp.home.views

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.databinding.FragmentProfileBinding
import com.oip.carapp.home.viewmodel.ProfileViewModel
import com.oip.carapp.utils.Constants
import com.oip.carapp.utils.PreferencesHandler
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class ProfileFragment : BaseFragment() {

    private val TAG = "ProfileFragment"

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

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
        }

        binding.done.setOnClickListener {
            editModeOff()
        }

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.getProfile()

        viewModel.profileData.observe(viewLifecycleOwner, Observer {
            Log.d(TAG, "onCreateView: $it")

            setProfileData(it)
        })

        Picasso.get().load(Constants.BASE_URL_IMAGES + PreferencesHandler.getProfileImageUrl())
            .placeholder(R.drawable.profile_placeholder).into(binding.profileImage)

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
        }
    }
}