package com.example.englishmessenger

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.parcel.Parcelize
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RegisterButton.setOnClickListener {
            performRegister()
            val intent = Intent(this, ProfileActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }
        AlreadyRegister.setOnClickListener {
            Log.d("RegisterActivity", "Try to show login activity")

            val intent = Intent(this, LoginAcvtivity::class.java)
            startActivity(intent)
        }
    }

    private fun performRegister() {
        val email = EmailAddress.text.toString()
        val password = TextPassword.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("RegisterActivity", "Email is: " + email)
        Log.d("RegisterActivity", "Password: $password")

        // Firebase authentication to create a user with email and password
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if(!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Successfully created user with uid: ${it.result.user?.uid}")
            }
            .addOnFailureListener {
                Log.d("Main", "Failed to create user: ${it.message}")
                Toast.makeText(this, "Please enter text in email/password", Toast.LENGTH_SHORT).show()
            }
        saveUserToFirebaseDatabase()
    }

    private fun saveUserToFirebaseDatabase() {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/usersKotlin/$uid")

        val user = User(uid, PersonName.text.toString())
        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("RegisterActivity", "Finally we saved the user to Firebase Database")
            }
            .addOnFailureListener {
                Log.d("RegisterActivity", "Failed to set value to database: ${it.message}")
            }
    }
}

@Parcelize
class User(val uid: String, val username: String): Parcelable{
    constructor() : this("", "")
}