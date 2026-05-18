package com.example.messengerapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.messengerapp.data.Conversation

@Dao
interface MessageDao {

    @Insert
    suspend fun insertMessage(message: MessageEntity)

    @Query("SELECT sender, body AS lastMessage, MAX(timeStamp) AS lastTimeStamp FROM messages GROUP BY sender ORDER BY lastTimeStamp DESC")
    suspend fun getAllMessages(): List<Conversation>

    @Query("SELECT * FROM messages WHERE sender= :sender ORDER BY timestamp ASC")
    suspend fun getMessageBySender(sender: String): List<MessageEntity>
}