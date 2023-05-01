package com.example.englishmessenger

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginAcvtivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        SignIn_button.setOnClickListener {
            val email = SignEmail.text.toString()
            val password = SignPassword.text.toString()

            Log.d("Login", "Attempt login with email/password: $email/***")

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener()
//                .add
        }

        SignIn_back.setOnClickListener {
            finish()
        }
    }
}