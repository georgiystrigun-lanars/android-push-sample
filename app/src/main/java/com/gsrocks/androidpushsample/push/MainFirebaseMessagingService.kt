package com.gsrocks.androidpushsample.push

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.gsrocks.androidpushsample.data.PushRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MainFirebaseMessagingService"

@AndroidEntryPoint
class MainFirebaseMessagingService : FirebaseMessagingService() {

    @Inject
    lateinit var pushRepository: PushRepository

    private val serviceScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "Received message: \n${message.describe()}")
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        serviceScope.launch(Dispatchers.IO) {
            pushRepository.sendPushToken(token)
        }
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
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
