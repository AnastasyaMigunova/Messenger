package com.example.englishmessenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_profile)

        languageTest_button.setOnClickListener {
            val intent1 = Intent(this, TestActivity::class.java)
            startActivity(intent1)
        }

        training_button.setOnClickListener {
            val intent2 = Intent(this, TrainingActivity::class.java)
            startActivity(intent2)
        }
    }
}