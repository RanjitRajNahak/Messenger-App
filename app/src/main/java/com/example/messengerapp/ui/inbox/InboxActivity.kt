package com.example.messengerapp.ui.inbox

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.data.local.AppDatabase
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.os.Build
import com.example.messengerapp.ui.chat.ChatActivity
import com.example.messengerapp.ui.contacts.ContactsActivity
import com.example.messengerapp.R

class InboxActivity : AppCompatActivity() {

    private lateinit var rvInbox: RecyclerView
    private lateinit var fabContacts: FloatingActionButton
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_inbox)

        requestPermissions()

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.title = "Inbox"

        rvInbox= findViewById(R.id.rvInbox)
        fabContacts = findViewById(R.id.fabContacts)

        rvInbox.layoutManager = LinearLayoutManager(this)

        loadConversations()

        fabContacts.setOnClickListener {
            val intent = Intent(this, ContactsActivity::class.java)
            startActivity(intent)
        }
    }
    private fun requestPermissions() {
        val permissions = mutableListOf(
            Manifest.permission.SEND_SMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.READ_CONTACTS
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }

        val launcher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()) {}

        if (permissions.any {
                ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
            }) {
            launcher.launch(permissions.toTypedArray())
        }
    }
    private fun loadConversations() {
        CoroutineScope(Dispatchers.IO).launch {
            val conversations = AppDatabase
                .getDatabase(applicationContext)
                .messageDao()
                .getAllMessages()

            withContext(Dispatchers.Main) {
                rvInbox.adapter = InboxAdapter(conversations) { conversation ->
                    val intent = Intent(this@InboxActivity, ChatActivity::class.java)
                    intent.putExtra("sender", conversation.sender)
                    startActivity(intent)
                }
            }
        }
    }
}