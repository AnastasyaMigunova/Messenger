package com.example.englishmessenger

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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

    val adapter = GroupAdapter<GroupieViewHolder>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_log)

        recyclerview_chat_log.adapter = adapter

        //val username = intent.getStringExtra(NewMessageActivity.USER_KEY)
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        supportActionBar?.title =  user?.username

        //setupDummyData()
        listenForMessages()

        send_button_chat_log.setOnClickListener {
            Log.d(TAG, "Attempt to send message...")
            performSendMessage()
        }

    }

    private fun listenForMessages(){
        val ref = FirebaseDatabase.getInstance().getReference("/messageKotlin")

        ref.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                val chatMessage = snapshot.getValue(ChatMessage::class.java)

                if(chatMessage != null) {
                    Log.d(TAG, chatMessage.text)

                    if (chatMessage.fromId == FirebaseAuth.getInstance().uid){
                        adapter.add(ChatFromItem(chatMessage.text))
                    } else {
                        adapter.add(ChatToItem(chatMessage.text))
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {

            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    class ChatMessage(val id: String, val text: String, val fromId: String, val toId: String,
                      val timestamp: Long){
        constructor() : this("", "", "", "", -1)
    }
    private fun performSendMessage(){
        val text = edittext_chat_log.text.toString()

        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<User>(NewMessageActivity.USER_KEY)
        val toId = user!!.uid

        if(fromId == null) return

        val reference = FirebaseDatabase.getInstance().getReference("/messageKotlin").push()

        val chatMessage = ChatMessage(reference.key!!, text, fromId, toId, System.currentTimeMillis() / 1000)
        reference.setValue(chatMessage)
            .addOnSuccessListener {
                Log.d(TAG, "Saved our chat message: ${reference.key}")
            }
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