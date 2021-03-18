package com.oip.carapp.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.ProgressBar
import androidx.databinding.DataBindingUtil
import com.oip.carapp.R
import com.oip.carapp.authentication.model.AuthResponse
import com.oip.carapp.databinding.ActivitySignInBinding
import com.oip.carapp.home.MainActivity
import com.oip.carapp.retrofit.BaseResponse
import com.oip.carapp.retrofit.RetrofitClient
import com.oip.carapp.utils.PreferencesHandler
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar
import com.oip.carapp.utils.toast
import kotlinx.android.synthetic.main.activity_sign_in.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding
    private lateinit var progress: ProgressBar

    private val TAG = "SignInActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        progress = binding.progress

        signIn.setOnClickListener {
            val email = email.text.toString().trim()
            val password = password.text.toString().trim()

            if (isValid(email, password)) {
                login(email, password)
            }
        }
        usePhone.setOnClickListener {
            startActivity(Intent(this, PhoneSignInActivity::class.java))
        }
        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }

    private fun isValid(email: String, password: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            toast("Email must not be empty")
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast("Email is invalid")
            return false
        }

        if (TextUtils.isEmpty(password)) {
            toast("Password must not be empty")
            return false
        } else if (password.length < 6) {
            toast("Password is short")
            return false
        }
        return true
    }

    private fun login(email: String, password: String) {
        showProgressBar(window, progress)
        RetrofitClient.apiInterface.login(email, password)
            .enqueue(object : Callback<BaseResponse<AuthResponse>> {
                override fun onFailure(
                    call: Call<BaseResponse<AuthResponse>>,
                    t: Throwable
                ) {
                    hideProgressBar(window, progress)
                    toast(t.message!!)
                    Log.e(TAG, t.message!!)
                }

                override fun onResponse(
                    call: Call<BaseResponse<AuthResponse>>,
                    response: Response<BaseResponse<AuthResponse>>
                ) {
                    hideProgressBar(window, progress)
                    response.body()?.apply {
                        if (success) {
                            //toast(message)
                            Log.d(TAG, message)
                            PreferencesHandler.setIsLogin(true)
                            val intent =
                                Intent(this@SignInActivity, MainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        } else {
                            Log.d(TAG, message)
                            toast(message)
                        }
                    }
                }
            })
    }
}