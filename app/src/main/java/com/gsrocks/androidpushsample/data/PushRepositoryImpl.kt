package com.gsrocks.androidpushsample.data

import com.gsrocks.androidpushsample.data.model.PushModel
import com.gsrocks.androidpushsample.data.model.PushTokenModel
import javax.inject.Inject

class PushRepositoryImpl @Inject constructor(
    private val api: PushApi
) : PushRepository {
    override suspend fun sendPushToken(token: String): Result<Unit> {
        return try {
            api.sendPushToken(PushTokenModel(token))
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }

    override suspend fun requestPush(title: String, body: String): Result<Unit> {
        return try {
            api.requestPush(PushModel(title, body))
            Result.success(Unit)
        } catch (e: Throwable) {
            Result.failure(e)
        }
    }
}
