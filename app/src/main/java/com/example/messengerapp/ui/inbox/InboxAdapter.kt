package com.example.messengerapp.ui.inbox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.messengerapp.R
import com.example.messengerapp.data.Conversation

class InboxAdapter(
    private val conversations: List<Conversation>,
    private val onClick: (Conversation) -> Unit
) : RecyclerView.Adapter<InboxAdapter.ConversationViewHolder>(){

    class ConversationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSender: TextView = itemView.findViewById(R.id.tvSender)
        val tvLastMessage: TextView = itemView.findViewById(R.id.tvLastMessage)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ConversationViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(
                R.layout.item_conversation,
                parent, false)

        return ConversationViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ConversationViewHolder,
        position: Int
    ) {
        val conversation = conversations[position]

        holder.tvSender.text = conversation.sender
        holder.tvLastMessage.text = conversation.lastMessage
        holder.itemView.setOnClickListener {
            onClick(conversation)
        }
    }

    override fun getItemCount(): Int {
        return conversations.size
    }
}