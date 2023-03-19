package com.gsrocks.androidpushsample.data

import com.gsrocks.androidpushsample.data.model.PushModel
import com.gsrocks.androidpushsample.data.model.PushTokenModel
import retrofit2.http.Body
import retrofit2.http.POST

interface PushApi {

    @POST("/push-tokens")
    suspend fun sendPushToken(@Body tokenModel: PushTokenModel)

    @POST("/push")
    suspend fun requestPush(@Body pushModel: PushModel)
}
