package com.example.englishmessenger

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        languageTest_button.setOnClickListener {
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }

        training_button.setOnClickListener {
            val intent = Intent(this, TrainingActivity::class.java)
            startActivity(intent)
        }

        chats_text.setOnClickListener {
            val intent = Intent(this, LatestMessagesActivity::class.java)
            startActivity(intent)
        }

        logout_button.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)
        }

        dictionary_button.setOnClickListener {
            val intent = Intent(this, DictionaryActivity::class.java)
            startActivity(intent)
        }

    }
}