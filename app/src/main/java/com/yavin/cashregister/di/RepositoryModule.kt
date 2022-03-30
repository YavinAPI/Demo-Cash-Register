package com.yavin.macewindu.di

import com.yavin.cashregister.service.repository.TransactionRepository
import com.yavin.cashregister.service.repository.TransactionRepositoryImpl
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