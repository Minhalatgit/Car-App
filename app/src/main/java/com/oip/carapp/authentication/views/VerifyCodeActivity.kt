package com.oip.carapp.authentication.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.oip.carapp.home.MainActivity
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityVerifyCodeBinding
import com.oip.carapp.utils.toast
import kotlinx.android.synthetic.main.activity_verify_code.*


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

        verify.setOnClickListener {


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

    private fun isValid(email: String, code: String): Boolean {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email must not be empty", Toast.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toast("Email is invalid")
            return false
        }
        if (TextUtils.isEmpty(code)) {
            Toast.makeText(this, "Please enter code", Toast.LENGTH_SHORT).show()
            return false
        } else if (code.length < 6) {
            toast("Please complete the code")
            return false
        }
        return true
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