package com.oip.carapp.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.Profile
import com.facebook.login.LoginManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.oip.carapp.R
import com.oip.carapp.databinding.ActivityAuthenticationBinding
import kotlinx.android.synthetic.main.activity_authentication.*
import java.util.*

class AuthenticationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthenticationBinding

    private lateinit var callbackManager: CallbackManager

    private val RC_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Google sign in object
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Facebook sign in object
        callbackManager = CallbackManager.Factory.create()

        val accessToken = AccessToken.getCurrentAccessToken()
        val isFbLoggedIn = accessToken != null && accessToken.isExpired
        Log.d("Authentication", "onCreate: $isFbLoggedIn")
        Log.d("Authentication", "onCreate: ${Profile.getCurrentProfile().firstName}")

        binding = DataBindingUtil.setContentView(this, R.layout.activity_authentication)
        binding.phone.setOnClickListener {
            startActivity(Intent(this, SignInActivity::class.java))
        }
        anotherWay.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        google.setOnClickListener {
            val signInIntent = mGoogleSignInClient.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
        facebook.setOnClickListener {
            LoginManager.getInstance()
                .logInWithReadPermissions(this, listOf("email", "public_profile"))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleSignInResult(task)
            } else {
                Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.result
            Log.d("Authentication", "User info ${account?.email} ${account?.displayName}")
            Toast.makeText(this, "Sign in successful", Toast.LENGTH_SHORT).show()
        } catch (e: ApiException) {
            Log.d("Authentication", "signInResult:failed code=" + e.statusCode)
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
        }
    }
}