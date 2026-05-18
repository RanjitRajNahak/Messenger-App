package com.example.messengerapp.ui.contacts

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.ui.chat.ChatActivity
import com.example.messengerapp.R
import com.example.messengerapp.contentprovider.getContacts
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ContactsActivity : AppCompatActivity() {
    private lateinit var toolbar: Toolbar
    private lateinit var rvContacts: RecyclerView
    private val contactPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { granted ->
        if (granted) {
            loadContacts()
        } else {
            Toast.makeText(this, "Contacts permission denied", Toast.LENGTH_SHORT).show()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contacts)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        rvContacts = findViewById(R.id.rvContacts)
        rvContacts.layoutManager = LinearLayoutManager(this)

        checkContactPermission()

    }

    private fun checkContactPermission() {
        val permission = Manifest.permission.READ_CONTACTS

        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            loadContacts()
        } else {
            contactPermissionLauncher.launch(permission)
        }
    }
    private fun loadContacts() {
        CoroutineScope(Dispatchers.IO).launch {
            val contacts = getContacts(applicationContext)

            withContext(Dispatchers.Main) {
                rvContacts.adapter = ContactAdapter(contacts) { contact ->
                    val number = contact.second

                    val intent = Intent(this@ContactsActivity, ChatActivity::class.java)
                    intent.putExtra("sender", number)
                    startActivity(intent)
                }
            }
        }
    }
}