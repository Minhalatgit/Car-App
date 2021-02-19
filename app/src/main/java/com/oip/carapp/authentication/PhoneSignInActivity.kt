package com.oip.carapp.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityPhoneSignInBinding

class PhoneSignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPhoneSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_sign_in)

        binding.next.setOnClickListener {
            startActivity(Intent(this, VerifyCodeActivity::class.java))
        }
    }
}