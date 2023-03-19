package com.gsrocks.androidpushsample.data

interface PushRepository {

    suspend fun sendPushToken(token: String): Result<Unit>

    suspend fun requestPush(title: String, body: String): Result<Unit>
}
