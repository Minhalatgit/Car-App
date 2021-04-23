package com.oip.carapp.authentication.views

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TypefaceSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oip.carapp.CustomTypefaceSpan
import com.oip.carapp.R
import com.oip.carapp.authentication.viewmodel.SignUpViewModel
import com.oip.carapp.databinding.ActivitySignUpBinding
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar
import com.oip.carapp.utils.toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.signIn

class SignUpActivity : AppCompatActivity() {

    private val TAG = "SignUpActivity"

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var viewModel: SignUpViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setSignUpLabelStyle()

        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        viewModel.result.observe(this, Observer {
            hideProgressBar(window, progress)
            Log.d(TAG, "Result $it")
            if (it.isValid) {
                toast(it.message)
                val intent = Intent(this@SignUpActivity, VerifyCodeActivity::class.java)
                intent.putExtra("email", email.text.toString().trim())
                startActivity(intent)
            } else {
                toast(it.message)
            }
        })

        signIn.setOnClickListener {
            onBackPressed()
        }

        signUp.setOnClickListener {
            showProgressBar(window, progress)
            viewModel.register(
                email.text.toString().trim(),
                password.text.toString().trim(),
                confirmPassword.text.toString().trim()
            )
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