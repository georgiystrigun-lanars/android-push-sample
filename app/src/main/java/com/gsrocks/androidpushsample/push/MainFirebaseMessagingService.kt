package com.gsrocks.androidpushsample.push

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "MainFirebaseMessagingService"

class MainFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "Received message: \n${message.describe()}")
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        // TODO: send token to server
    }
}

fun RemoteMessage.describe() = buildString {
    val message = this@describe

    append("  messageId: ${message.messageId}\n")
    append("  messageType: ${message.messageType}\n")
    append("  from: ${message.from}\n")
    append("  to: ${message.to}\n")
    append("  priority: ${message.priority}\n")
    append("  originalPriority: ${message.originalPriority}\n")
    append("  senderId: ${message.senderId}\n")
    append("  sentTime: ${message.sentTime}\n")
    append("  ttl: ${message.ttl}\n")
    append("  collapseKey: ${message.collapseKey}\n")
    append("  notification: ${message.notification?.describe()}\n")
    append("  data: ${message.data}")
}

fun RemoteMessage.Notification.describe() = buildString {
    val notification = this@describe

    append("\n")
    append("    title: ${notification.title}\n")
    append("    body: ${notification.body}\n")
    append("    imageUrl: ${notification.imageUrl}\n")
    append("    channelId: ${notification.channelId}\n")
    append("    notificationPriority: ${notification.notificationPriority}")
}
