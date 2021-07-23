package com.oip.carapp.home.views

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.dhaval2404.imagepicker.ImagePicker
import com.oip.carapp.BaseFragment
import com.oip.carapp.R
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.databinding.FragmentProfileBinding
import com.oip.carapp.home.viewmodel.ProfileViewModel
import com.oip.carapp.utils.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import me.mutasem.booleanselection.BooleanSelectionView
import java.io.File
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ProfileFragment : BaseFragment() {

    private val TAG = "ProfileFragment"

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel

    private var profileData: AuthResponse? = null
    private var fileUri: Uri? = null

    private var isEditMode = false

    private lateinit var defaultEditTextBg: Drawable
    private var selectedGender = ""

    val RESULT_IMAGE_MULTIPLE = 1
    private var imageUris = ArrayList<Uri>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        viewModel.getProfile()

        defaultEditTextBg = binding.usernameTwo.background

        editModeOff()

        binding.edit.setOnClickListener {
            editModeOn()
        }

        binding.cancel.setOnClickListener {
            editModeOff()
            if (profileData != null)
                setProfileData(profileData!!)
        }

        binding.done.setOnClickListener {
            viewModel.updateProfile(
                binding.usernameTwo.text.toString().trim(),
                binding.phone.text.toString().trim(),
                selectedGender,
                getDate(
                    binding.birthday.text.toString(), BIRTHDAY_DATE_FORMAT,
                    SERVER_BIRTHDAY_DATE_FORMAT
                ),
                PreferencesHandler.getUserId()!!,
                if (fileUri == null) null else File(fileUri?.path!!) // if fileuri is empty send null
            )
        }

        binding.uploadIcon.setOnClickListener {
            ImagePicker.with(this)
                .crop()                    //Crop image(Optional), Check Customization for more option
                .compress(1024) //Final image size will be less than 1 MB(Optional)
                .maxResultSize(
                    1080,
                    1080
                )    //Final image resolution will be less than 1080 x 1080(Optional)
                .start()

//            pickImagesIntent()

        }

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
        viewModel.result.observe(viewLifecycleOwner, Observer {
            if (!it.isValid) {
                requireContext().toast(it.message)

            } else {
                editModeOff()
                showProgressBar(window, binding.progress)
            }
        })

        binding.gender.setSelectionListener { selection, selectedText ->
            Log.d(TAG, "Selected text at: $selection is $selectedText")
            selectedGender = selectedText
        }

        return binding.root
    }

    private fun editModeOn() {
        isEditMode = true
        binding.uploadIcon.visibility = View.VISIBLE
        binding.done.visibility = View.VISIBLE
        binding.cancel.visibility = View.VISIBLE
        binding.edit.visibility = View.GONE
        binding.profileImage.alpha = 0.5f

        binding.usernameTwo.isEnabled = true
        binding.usernameTwo.requestFocus()

        binding.phone.isEnabled = true

        binding.usernameTwo.background = defaultEditTextBg
        binding.phone.background = defaultEditTextBg

        binding.gender.findViewById<RadioButton>(R.id.viewStart).isEnabled = true
        binding.gender.findViewById<RadioButton>(R.id.viewEnd).isEnabled = true

        binding.birthday.transformIntoDatePicker(requireContext(), "EEE, dd MMM yyyy", Date())
    }

    private fun editModeOff() {
        isEditMode = false
        binding.uploadIcon.visibility = View.GONE
        binding.done.visibility = View.GONE
        binding.cancel.visibility = View.GONE
        binding.edit.visibility = View.VISIBLE
        binding.profileImage.alpha = 1.0f

        binding.usernameTwo.isEnabled = false
        binding.phone.isEnabled = false

        binding.usernameTwo.background = null
        binding.phone.background = null

        binding.gender.findViewById<RadioButton>(R.id.viewStart).isEnabled = false
        binding.gender.findViewById<RadioButton>(R.id.viewEnd).isEnabled = false

        binding.birthday.setOnClickListener(null)
    }

    private fun setProfileData(profileData: AuthResponse) {
        selectedGender = profileData.gender
        binding.apply {
            username.text = profileData.name ?: "N/A"
            usernameTwo.setText(profileData.name ?: "N/A")
            phone.setText(profileData.phone ?: "N/A")
            email.text = profileData.email
            gender.selection = if (profileData.gender.equals(
                    "male",
                    true
                )
            ) BooleanSelectionView.Selection.Start else BooleanSelectionView.Selection.End
            if (profileData.birthday == null) {
                birthday.text = "N/A"
            } else {
                birthday.text = getDate(
                    profileData.birthday,
                    SERVER_BIRTHDAY_DATE_FORMAT,
                    BIRTHDAY_DATE_FORMAT
                )
            }
            requireContext().loadImage(binding.profileImage, profileData.image!!)

            requireActivity().updateProfilePicture()
        }
    }

    private fun pickImagesIntent() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Select images"),
            RESULT_IMAGE_MULTIPLE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "onActivityResult: Called $data")
//        if (resultCode == RESULT_OK && requestCode == RESULT_IMAGE_MULTIPLE) {
//            if (data?.clipData != null) {
//                //picked multiple images
//                val selectedImageCount = data.clipData?.itemCount
//                imageUris.clear()
//                for (i in 0 until selectedImageCount!!) {
//                    val imagerUri = data.clipData?.getItemAt(i)?.uri
//                    imageUris.add(imagerUri!!)
//                }
//
//                binding.profileImage.setImageURI(imageUris[0])
//
//            } else {
//                //picked single image
//            }
//        }


        when (resultCode) {
            RESULT_OK -> {
                val uri: Uri = data?.data!!
                fileUri = uri
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