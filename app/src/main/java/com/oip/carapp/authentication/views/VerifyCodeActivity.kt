package com.oip.carapp.authentication.views

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.oip.carapp.R
import com.oip.carapp.authentication.viewmodel.VerifyCodeViewModel
import com.oip.carapp.databinding.ActivityVerifyCodeBinding
import com.oip.carapp.home.MainActivity
import com.oip.carapp.utils.hideKeyboard
import com.oip.carapp.utils.hideProgressBar
import com.oip.carapp.utils.showProgressBar
import com.oip.carapp.utils.toast
import kotlinx.android.synthetic.main.activity_verify_code.*

class VerifyCodeActivity : AppCompatActivity() {

    private val TAG = "VerifyCodeActivity"

    private lateinit var binding: ActivityVerifyCodeBinding
    private lateinit var viewModel: VerifyCodeViewModel

    private lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_verify_code)

        viewModel = ViewModelProvider(this).get(VerifyCodeViewModel::class.java)

        if (intent.hasExtra("email"))
            email = intent.getStringExtra("email")!!

        viewModel.result.observe(this, Observer {
            Log.d(TAG, "Result observer called")
            hideProgressBar(window, progress)
            if (it.isValid) {
                toast(it.message)
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            } else {
                toast(it.message)
            }
        })

        initViews()

        verify.setOnClickListener {
            hideKeyboard()
            val code = otpOne.text.toString() + otpTwo.text + otpThree.text + otpFour.text
            Log.d(TAG, code)
            if (this::email.isInitialized) {
                Log.d(TAG, "Email $email $code")
                showProgressBar(window, progress)
                viewModel.verify(email, code)
            }
        }

    }

    private fun initViews() {
        otpOne.addTextChangedListener(GenericTextWatcher(otpOne))
        otpTwo.addTextChangedListener(GenericTextWatcher(otpTwo))
        otpThree.addTextChangedListener(GenericTextWatcher(otpThree))
        otpFour.addTextChangedListener(GenericTextWatcher(otpFour))
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