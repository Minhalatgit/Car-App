package com.oip.carapp.authentication.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oip.carapp.R
import com.oip.carapp.authentication.viewmodel.LoginViewModel
import com.oip.carapp.databinding.ActivityLoginBinding
import com.oip.carapp.home.MainActivity
import com.oip.carapp.utils.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    private val TAG = "LoginActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        viewModel.result.observe(this, Observer {
            Log.d(TAG, "Observer called")
            hideProgressBar(window, progress)
            if (it.isValid) {
                subscribeToAllTopic()//FCM subscription
                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                Log.d(TAG, it.message)
            } else {
                toast(it.message)
            }
        })

        signIn.setOnClickListener {
            hideKeyboard()
            showProgressBar(window, progress)
            viewModel.login(email.text.toString().trim(), password.text.toString().trim())
        }
        signUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
    }
}