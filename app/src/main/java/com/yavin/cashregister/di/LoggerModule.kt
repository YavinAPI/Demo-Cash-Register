package com.yavin.macewindu.di

import com.yavin.macewindu.logging.ILogger
import com.yavin.macewindu.logging.YLogger
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LoggerModule {
    @Binds
    @Singleton
    abstract fun bindLogger(
        loggerImplementation: YLogger
    ): ILogger
}