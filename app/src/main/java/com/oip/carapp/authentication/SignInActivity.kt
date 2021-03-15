package com.oip.carapp.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivitySignInBinding
import com.oip.carapp.home.MainActivity
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_in)

        signIn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
        usePhone.setOnClickListener {
            startActivity(Intent(this, PhoneSignInActivity::class.java))
        }
    }
}