package com.example.messengerapp.broadcastreceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import com.example.messengerapp.notification.SmsNotificationHelper
import com.example.messengerapp.service.MessageService

class SmsReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val safeContext = context ?: return

        if (intent?.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {

            val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)

            for (sms in messages) {
                val sender = sms.originatingAddress ?: "Unknown"

                val body = sms.messageBody

                //Show Notification
                SmsNotificationHelper.showSmsNotification(
                    safeContext,
                    sender,
                    body
                )

                //Send SMS data to Service
                val serviceIntent = Intent(context, MessageService::class.java)
                serviceIntent.putExtra("sender", sender)
                serviceIntent.putExtra("body", body)
                context.startService(serviceIntent)
            }
        }
    }

}