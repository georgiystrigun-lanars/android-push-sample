package com.gsrocks.androidpushsample.push

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.RemoteMessage.Notification
import com.gsrocks.androidpushsample.R
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

    private val notificationManager by lazy {
        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "Received message: \n${message.describe()}")
        message.notification?.let {
            showNotification(it, notificationId = message.messageId?.hashCode() ?: 0)
        }
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

    private fun showNotification(remoteNotification: Notification, notificationId: Int) {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(this, "notifications")
            .setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(remoteNotification.title)
            .setContentText(remoteNotification.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        notificationManager.notify(notificationId, notification)
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            "notifications",
            "Push sample app notifications",
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
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
