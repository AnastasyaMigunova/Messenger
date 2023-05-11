package com.example.englishmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieAdapter;


class ChatLogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "Chat Log"

        //val adapter = GroupieAdapter<ViewHolder>()

    }
}