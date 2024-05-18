package com.example.otpexample

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.otpexample.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    lateinit var binding : ActivityMainBinding
    lateinit var auth : FirebaseAuth
    var currentUser : FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser


    }

    fun logout(view: View) {
        auth.signOut()
        login()
    }

    private fun login() {
        val intent = Intent(this@MainActivity,Login::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        if (currentUser==null){
            login()
        }else{
            var userInfo = currentUser!!.phoneNumber
            binding.otpTextView.text = "Welcome $userInfo"
        }
    }
}