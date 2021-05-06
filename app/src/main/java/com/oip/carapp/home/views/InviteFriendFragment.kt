package com.oip.carapp.home.views

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.oip.carapp.BaseFragment
import com.oip.carapp.CustomTypefaceSpan
import com.oip.carapp.R
import com.oip.carapp.databinding.FragmentInviteFriendBinding

class InviteFriendFragment : BaseFragment() {

    lateinit var binding: FragmentInviteFriendBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInviteFriendBinding.inflate(inflater, container, false)


        setInviteFriendStyle()

        return binding.root
    }

    private fun setInviteFriendStyle() {
        val text = "Earn up to $150 a day"
        val spannableString = SpannableString(text)
        val poppinsBold = ResourcesCompat.getFont(requireContext(), R.font.poppins_bold)
        val poppinsBoldSpan: TypefaceSpan = CustomTypefaceSpan("", poppinsBold)
        spannableString.setSpan(poppinsBoldSpan, 12, 15, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.earn.text = spannableString
    }

}