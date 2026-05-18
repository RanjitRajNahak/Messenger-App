package com.example.messengerapp.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.messengerapp.R
import com.example.messengerapp.ui.chat.ChatActivity

object SmsNotificationHelper {
    private const val CHANNEL_ID = "sms_channel"
    private const val CHANNEL_NAME = "SMS Notification"

    fun showSmsNotification(
        context: Context,
        sender: String,
        body: String
    ) {
        createNotificationChannel(context)

        val chatIntent = Intent(context, ChatActivity::class.java)
        chatIntent.putExtra("sender", sender)
        chatIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP

        val pendingIntent = PendingIntent.getActivity(
            context,
            sender.hashCode(),
            chatIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.logo_messenger)
            .setContentTitle(sender)
            .setContentText(body)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText(body)
            )
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE)  as NotificationManager

            notificationManager.createNotificationChannel(channel)
        }
    }
}