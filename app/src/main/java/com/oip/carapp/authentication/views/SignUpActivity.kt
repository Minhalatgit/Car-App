package com.oip.carapp.authentication.views

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextUtils
import android.text.style.TypefaceSpan
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.hbb20.CountryCodePicker
import com.oip.carapp.CustomTypefaceSpan
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivitySignUpBinding
import com.oip.carapp.home.MainActivity
import com.oip.carapp.utils.toast
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.signIn

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        setSignUpLabelStyle()

        signIn.setOnClickListener {
            onBackPressed()
        }

        signUp.setOnClickListener {
            startActivity(
                Intent(
                    this@SignUpActivity,
                    VerifyCodeActivity::class.java
                )
            )

//            val email = email.text.toString().trim()
//            val password = password.text.toString().trim()
//            val phone = phone.text.toString().replace("[\\s\\-]".toRegex(), "")
//            if (isValid(email, password, phone)) {
//
//                RetrofitClient.apiInterface.register(email, password, countryCode + phone)
//                    .enqueue(object : Callback<BaseResponse<AuthResponse>> {
//                        override fun onFailure(
//                            call: Call<BaseResponse<AuthResponse>>,
//                            t: Throwable
//                        ) {
//                            toast(t.message!!)
//                        }
//
//                        override fun onResponse(
//                            call: Call<BaseResponse<AuthResponse>>,
//                            response: Response<BaseResponse<AuthResponse>>
//                        ) {
//                            response.body()?.apply {
//                                if (success) {
//                                    toast(message)
//                                    startActivity(
//                                        Intent(
//                                            this@SignUpActivity,
//                                            VerifyCodeActivity::class.java
//                                        )
//                                    )
//                                } else {
//                                    toast(message)
//                                }
//                            }
//                        }
//
//                    })
//            }
        }

    }

    private fun isValid(email: String, password: String, number: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email must not be empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast("Email is invalid")
            return false
        }
        if (TextUtils.isEmpty(number)) {
            Toast.makeText(this, "Number must not be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Password must not be empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (password.length < 6) {
            toast("Password is short")
            return false
        }
        return true
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