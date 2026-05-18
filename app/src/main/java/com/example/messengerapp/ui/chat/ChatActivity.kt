package com.example.messengerapp.ui.chat

import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.example.messengerapp.data.local.AppDatabase
import com.example.messengerapp.service.MessageService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatActivity : AppCompatActivity() {

    private lateinit var sender: String
    private lateinit var rvMessages: RecyclerView
    private lateinit var etReplyMessage: EditText
    private lateinit var btnSendReply: ImageButton
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)

        sender = intent.getStringExtra("sender") ?: ""

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        rvMessages = findViewById(R.id.rvMessages)
        etReplyMessage = findViewById(R.id.etReplyMessage)
        btnSendReply = findViewById(R.id.btnSendReply)

        toolbar.title = sender

        rvMessages.layoutManager = LinearLayoutManager(this)

        loadMessages()

        btnSendReply.setOnClickListener{
            sendReply()
        }
    }

    private fun loadMessages() {
        CoroutineScope(Dispatchers.IO).launch {
            val messages = AppDatabase
                .getDatabase(applicationContext)
                .messageDao()
                .getMessageBySender(sender)

            withContext(Dispatchers.Main) {
                rvMessages.adapter = ChatMessageAdapter(messages)
            }
        }
    }

    private fun sendReply(){
        val message = etReplyMessage.text.toString()

        if (message.isNotEmpty() && sender.isNotEmpty()) {
            SmsManager.getDefault().sendTextMessage(
                sender,
                null,
                message,
                null,
                null
            )

            etReplyMessage.text.clear()

            val serviceIntent = Intent(this, MessageService::class.java)
            serviceIntent.putExtra("sender", sender)
            serviceIntent.putExtra("body", message)
            startActivity(serviceIntent)
        }
    }
}