package com.oip.carapp.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.oip.carapp.CustomTypefaceSpan
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivitySignUpBinding
import kotlinx.android.synthetic.main.activity_sign_up.*


class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        setSignUpLabelStyle()

        binding.signIn.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }

        signUp.setOnClickListener {
            startActivity(Intent(this, VerifyCodeActivity::class.java))
        }
    }

    private fun setSignUpLabelStyle() {
        val text = "Sign Up with email and phone number"
        val spannableString = SpannableString(text)
        val poppinsBold = ResourcesCompat.getFont(this, R.font.poppins_bold)
        val poppinsBoldSpan: TypefaceSpan = CustomTypefaceSpan("", poppinsBold)
        spannableString.setSpan(poppinsBoldSpan, 0, 7, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        binding.signUpLabel.text = spannableString
    }
}