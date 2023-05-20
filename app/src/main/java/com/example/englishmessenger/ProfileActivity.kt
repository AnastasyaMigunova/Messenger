package com.example.englishmessenger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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

        chats_text.setOnClickListener {
            val intent = Intent(this, LatestMessagesActivity::class.java)
            startActivity(intent)
        }



        random_dialogue_button.setOnClickListener {
            RandomUser()

        }

    }

    private fun RandomUser(){

        val currentUser = FirebaseAuth.getInstance().currentUser
        val currentUid = currentUser?.uid

        val databaseReference = FirebaseDatabase.getInstance().reference
        val usersRef = databaseReference.child("/usersKotlin")

        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                // Получить все дочерние элементы в коллекции "usersKotlin"
                val users = snapshot.children.map { it.getValue(User::class.java) }

                // Исключить текущего пользователя из списка пользователей
                val filteredUsers = users.filter { it?.uid != currentUid }

                if (filteredUsers.isNotEmpty()) {
                    val randomUser = filteredUsers.random()

                    val randomUserName = randomUser?.username
                    val randomUserUid = randomUser?.uid

                    Log.d("NAME","Имя случайного пользователя: $randomUserName")
                    Log.d("UID", "UID случайного пользователя: $randomUserUid")

                    val item = User(randomUserUid.toString(), randomUserName.toString())

                    val intent = Intent(this@ProfileActivity, ChatLogActivity::class.java)
                    intent.putExtra(NewMessageActivity.USER_KEY, item)
                    startActivity(intent)


                } else {
                    Log.d("Error (no names available)","Нет доступных пользователей для выбора.")
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }

}