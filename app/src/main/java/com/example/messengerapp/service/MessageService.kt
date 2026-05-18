package com.example.messengerapp.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.messengerapp.data.local.AppDatabase
import com.example.messengerapp.data.local.MessageEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MessageService: Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val sender = intent?.getStringExtra("sender")
        val body = intent?.getStringExtra("body")

        Log.d("MessageService", "SMS from $sender: $body")

        val message = MessageEntity(
            sender = sender,
            body = body,
            timestamp = System.currentTimeMillis()
        )

        CoroutineScope(Dispatchers.IO).launch {
            val db = AppDatabase.getDatabase(applicationContext)
            db.messageDao().insertMessage(message)

            stopSelf()
        }

        return START_NOT_STICKY
    }
    override fun onBind(p0: Intent?): IBinder? = null

}