package com.oip.carapp.home.views

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.authentication.views.LoginActivity
import com.oip.carapp.databinding.FragmentMoreBinding
import com.oip.carapp.utils.Constants
import com.oip.carapp.utils.PreferencesHandler
import com.oip.carapp.utils.loadImage
import com.oip.carapp.utils.unsubscribeToAllTopic
import com.squareup.picasso.Picasso

class MoreFragment : BaseFragment() {

    private lateinit var binding: FragmentMoreBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoreBinding.inflate(layoutInflater, container, false)

        requireContext().loadImage(binding.profileImage, PreferencesHandler.getProfileImageUrl()!!)

        binding.username.text = PreferencesHandler.getUsername()

        binding.profile.setOnClickListener {
            it.findNavController().navigate(R.id.action_moreFragment_to_profileFragment)
        }
        binding.notification.setOnClickListener {
            it.findNavController().navigate(R.id.action_moreFragment_to_notificationFragment)
        }
        binding.appointment.setOnClickListener {
            it.findNavController().navigate(R.id.action_moreFragment_to_appointmentFragment)
        }
        binding.setting.setOnClickListener {
            it.findNavController().navigate(R.id.action_moreFragment_to_settingsFragment)
        }
        binding.logout.setOnClickListener {
            unsubscribeToAllTopic()//FCM unsubscribe
            PreferencesHandler.setIsLogin(false)
            PreferencesHandler.clearPreferences()
            val intent = Intent(requireActivity(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        return binding.root
    }
}