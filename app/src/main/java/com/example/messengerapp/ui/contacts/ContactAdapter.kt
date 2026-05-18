package com.example.messengerapp.ui.contacts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R

class ContactAdapter(
    private val contacts: List<Pair<String, String>>,
    private val onClick: (Pair<String, String>) -> Unit
): RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvContactName: TextView = itemView.findViewById(R.id.tvContactName)
        val tvContactNumber: TextView = itemView.findViewById(R.id.tvContactNumber)
    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contacts[position]

        val name = contact.first
        val number = contact.second

        holder.tvContactName.text = name
        holder.tvContactNumber.text = number

        holder.itemView.setOnClickListener {
            onClick(contact)
        }

    }

    override fun getItemCount(): Int {
        return contacts.size
    }

}