package com.example.otpexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.otpexample.databinding.ActivityLoginBinding
import com.example.otpexample.databinding.ActivityOtpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider

class Otp : AppCompatActivity() {
    lateinit var binding: ActivityOtpBinding
    lateinit var auth : FirebaseAuth
    var currentUser : FirebaseUser? = null
    var phoneNumber :String = ""
    var authid : String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOtpBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser

        authid = intent.getStringExtra("otpcr")!!

    }
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information


                    val user = task.result?.user
                    sendHome()
                } else {
                    // Sign in failed, display a message and update the UI

                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    fun sendHome(){
        val intent = Intent(this@Otp,MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()
        if (currentUser != null){
            sendHome()
            finish()
        }
    }

    fun verify_otp(view: View) {
        val otp = binding.otpEditText.text.toString()
        if (otp.isNotEmpty()){
            val credential = PhoneAuthProvider.getCredential(authid,otp)
            signInWithPhoneAuthCredential(credential)
        }
    }
}