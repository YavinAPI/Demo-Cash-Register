package com.yavin.cashregister.application

import android.app.Application
import com.yavin.macewindu.logging.ILogger
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class CashRegisterApp : Application() {

    @Inject
    lateinit var logger: ILogger

}