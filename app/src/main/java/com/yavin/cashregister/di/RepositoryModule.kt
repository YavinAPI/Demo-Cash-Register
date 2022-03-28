package com.yavin.macewindu.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    /*@Binds
    @Singleton
    fun provideHistoryRepository(repository: TransactionRepositoryImpl): TransactionRepository

    @Binds
    @Singleton
    fun providePaymentRepository(repository: PaymentRepositoryImpl): PaymentRepository*/
}