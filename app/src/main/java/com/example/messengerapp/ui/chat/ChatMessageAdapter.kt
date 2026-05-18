package com.example.messengerapp.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.example.messengerapp.data.local.MessageEntity

class ChatMessageAdapter(private val messages: List<MessageEntity>
): RecyclerView.Adapter<ChatMessageAdapter.ChatMessageViewHolder>() {

    class ChatMessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvMessageBody: TextView = itemView.findViewById(R.id.tvMessageBody)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ChatMessageViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_chat_message, parent, false)

        return ChatMessageViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ChatMessageViewHolder,
        position: Int
    ) {
        val message = messages[position]
        holder.tvMessageBody.text = message.body
    }

    override fun getItemCount(): Int {
        return messages.size
    }



}