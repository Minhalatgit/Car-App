package com.oip.carapp.authentication.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.oip.carapp.CustomTypefaceSpan
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityForgotPasswordBinding
import com.oip.carapp.databinding.ActivitySignInBinding
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        setForgotPasswordStyle()
    }

    private fun setForgotPasswordStyle() {
        val text = "Forgot\nPassword"
        val spannableString = SpannableString(text)
        val poppinsBold = ResourcesCompat.getFont(this, R.font.poppins_bold)
        val poppinsBoldSpan: TypefaceSpan = CustomTypefaceSpan("", poppinsBold)
        spannableString.setSpan(poppinsBoldSpan, 0, 6, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        forgotPasswordLabel.text = spannableString
    }
}