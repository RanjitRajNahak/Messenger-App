package com.example.messengerapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.messengerapp.ui.inbox.InboxActivity


class MainActivity : AppCompatActivity() {

    private lateinit var btnOpenInbox: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnOpenInbox = findViewById(R.id.btnOpenInbox)

        btnOpenInbox.setOnClickListener {
            val intent = Intent(this, InboxActivity::class.java)
            startActivity(intent)
        }
    }


}