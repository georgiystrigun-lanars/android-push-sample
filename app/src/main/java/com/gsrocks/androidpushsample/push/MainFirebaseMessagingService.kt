package com.gsrocks.androidpushsample.push

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

private const val TAG = "MainFirebaseMessagingService"

class MainFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "Received message: $message")
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        // TODO: send token to server
    }
}
