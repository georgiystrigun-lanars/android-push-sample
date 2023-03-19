package com.gsrocks.androidpushsample.data

import com.gsrocks.androidpushsample.data.model.PushModel
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface PushApi {

    @POST("/push-tokens")
    suspend fun sendPushToken(@Query("token") token: String)

    @POST("/push")
    suspend fun requestPush(@Body pushModel: PushModel)
}
