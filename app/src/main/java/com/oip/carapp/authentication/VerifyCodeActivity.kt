package com.oip.carapp.authentication

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oip.carapp.home.MainActivity
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityVerifyCodeBinding


class VerifyCodeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVerifyCodeBinding

    private lateinit var otpOne: EditText
    private lateinit var otpTwo: EditText
    private lateinit var otpThree: EditText
    private lateinit var otpFour: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_code)
        initViews()

        binding.verify.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }

    private fun initViews() {
        otpOne = binding.otpOne
        otpTwo = binding.otpTwo
        otpThree = binding.otpThree
        otpFour = binding.otpFour

        otpOne.addTextChangedListener(GenericTextWatcher(otpOne));
        otpTwo.addTextChangedListener(GenericTextWatcher(otpTwo));
        otpThree.addTextChangedListener(GenericTextWatcher(otpThree));
        otpFour.addTextChangedListener(GenericTextWatcher(otpFour));
    }

    inner class GenericTextWatcher(private var view: View) : TextWatcher {
        override fun afterTextChanged(editable: Editable) {
            val text = editable.toString()
            when (view.id) {
                R.id.otpOne -> if (text.length == 1) otpTwo.requestFocus()
                R.id.otpTwo -> if (text.length == 1) otpThree.requestFocus() else if (text.isEmpty()) otpOne.requestFocus()
                R.id.otpThree -> if (text.length == 1) otpFour.requestFocus() else if (text.isEmpty()) otpTwo.requestFocus()
                R.id.otpFour -> if (text.isEmpty()) otpThree.requestFocus()
            }
        }

        override fun beforeTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) {

        }

        override fun onTextChanged(
            arg0: CharSequence,
            arg1: Int,
            arg2: Int,
            arg3: Int
        ) {
        }

    }

}