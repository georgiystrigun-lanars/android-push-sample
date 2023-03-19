package com.gsrocks.androidpushsample.di

import com.gsrocks.androidpushsample.data.PushRepository
import com.gsrocks.androidpushsample.data.PushRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindPushRepository(pushRepositoryImpl: PushRepositoryImpl): PushRepository
}
