package com.yavin.cashregister.logging

import android.util.Log
import com.yavin.cashregister.logging.ILogger
import javax.inject.Inject


class YLogger @Inject constructor() : ILogger {

    private val tagName = "YLogger"

    override fun d(tag: String, message: String) {
        try {
            Log.d(tag, message)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun i(tag: String, message: String) {
        try {
            Log.i(tag, message)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
}

    override fun w(tag: String, message: String) {
        try {
            Log.w(tag, message)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    override fun e(tag: String, message: String) {
        try {
            Log.e(tag, message)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

}

