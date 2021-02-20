package com.oip.carapp.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.oip.carapp.CustomTypefaceSpan
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityPhoneSignInBinding

class PhoneSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_sign_in)

        setSignInStyle()

        binding.next.setOnClickListener {
            startActivity(Intent(this, VerifyCodeActivity::class.java))
        }
    }

    private fun setSignInStyle() {
        val text = "Login with your"
        val spannableString = SpannableString(text)
        val poppinsBold = ResourcesCompat.getFont(this, R.font.poppins_bold)
        val poppinsBoldSpan: TypefaceSpan = CustomTypefaceSpan("", poppinsBold)
        spannableString.setSpan(poppinsBoldSpan, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.loginLabel.text = spannableString
    }
}