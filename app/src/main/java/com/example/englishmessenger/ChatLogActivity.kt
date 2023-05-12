package com.example.englishmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.GroupieAdapter;
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.activity_chat_log.*
import kotlinx.android.synthetic.main.chat_to_row.view.*


class ChatLogActivity : AppCompatActivity() {

    companion object{
        val TAG ="ChatLog"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        supportActionBar?.title = "Chat Log"

        //val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
        //supportActionBar?.title =  username

        setupDummyData()

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message...")
            performSendMessage()
        }

    }

    class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String,
                      val timestamp: Long)
    private fun performSendMessage(){
//        val text = edittext_chat_log.text.toString()
//
//        val fromId = FirebaseAuth.getInstance().uid
//        val toId =
//
//        val reference = FirebaseDatabase.getInstance().getReference("/message").push()
//
//        val chatMessage = ChatMessage(reference.key, text,)
//        reference.setValue(chatMessage)
//            .addOnSuccessListener {
//                Log.d(TAG, "Saved our chat message: ${reference.key}")
//            }
    }

    private fun setupDummyData(){
        val adapter = GroupAdapter<GroupieViewHolder>()

        adapter.add(ChatFromItem("FROM MESSSSSSSAGE"))
        adapter.add(ChatToItem("TO MESSSSSSAAAGE\nMESSSSSAGE"))
        adapter.add(ChatFromItem("FROM MESSSSSSSAGE"))
        adapter.add(ChatToItem("TO MESSSSSSAAAGE\nMESSSSSAGE"))
        adapter.add(ChatFromItem("FROM MESSSSSSSAGE"))
        adapter.add(ChatToItem("TO MESSSSSSAAAGE\nMESSSSSAGE"))

        recyclerview_chat_log.adapter = adapter
    }

}

class ChatFromItem(val text: String): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.textview_to_row.text = text
    }
    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}