package com.yavin.cashregister.di

import com.yavin.cashregister.repository.TransactionRepository
import com.yavin.cashregister.repository.TransactionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun provideHistoryRepository(repository: TransactionRepositoryImpl): TransactionRepository

}