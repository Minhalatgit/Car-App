package com.oip.carapp.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.TypefaceSpan
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.hbb20.CountryCodePicker
import com.oip.carapp.CustomTypefaceSpan
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityPhoneSignInBinding
import kotlinx.android.synthetic.main.activity_sign_up.*

class PhoneSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneSignInBinding
    private lateinit var countryCodePicker: CountryCodePicker
    private var countryCode: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_sign_in)
        countryCodePicker = binding.countryCodeSpinner
        setSignInStyle()

        binding.next.setOnClickListener {
            if (isValid(phone.text.toString().trim().replace("[\\s\\-]".toRegex(), ""))) {
                startActivity(Intent(this, VerifyCodeActivity::class.java))
            }
        }
        countryCodePicker.setOnCountryChangeListener {
            countryCode = countryCodePicker.selectedCountryCodeWithPlus
            countryCodePicker.registerCarrierNumberEditText(phone)
        }
    }

    private fun setSignInStyle() {
        val text = "Login with your phone"
        val spannableString = SpannableString(text)
        val poppinsBold = ResourcesCompat.getFont(this, R.font.poppins_bold)
        val poppinsBoldSpan: TypefaceSpan = CustomTypefaceSpan("", poppinsBold)
        spannableString.setSpan(poppinsBoldSpan, 0, 5, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.loginLabel.text = spannableString
    }

    private fun isValid(number: String): Boolean {
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Number must not be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}