package com.example.messengerapp.contentprovider

import android.content.Context
import android.provider.ContactsContract

fun getContacts(context: Context): List<Pair<String, String>> {

    val list = mutableListOf<Pair<String, String>>()
    val cursor = context.contentResolver.query(
        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
        null,
        null,
        null,
        null
    )

    cursor?.use {
        while (it.moveToNext()) {
            val name = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            list.add(Pair(name, number))
        }
    }
    return list
}