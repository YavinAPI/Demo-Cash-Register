package com.yavin.cashregister.network

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiFactory {

    @Singleton
    @Provides
    fun providesYavinApiService(retrofit: Retrofit): YavinApiService =
        retrofit.create(YavinApiService::class.java)


}