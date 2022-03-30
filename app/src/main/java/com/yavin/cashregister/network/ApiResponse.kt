package com.yavin.cashregister.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.yavin.cashregister.R
import com.yavin.cashregister.service.model.ApiError


sealed class ApiResponse<out T> {
    data class SUCCESS<T>(val data: T) : ApiResponse<T>()
    data class ERROR<T>(val errorType: ErrorType, val message: String? = null) : ApiResponse<T>()

    enum class ErrorType {
        NETWORK_ERROR,
        SERVER_ERROR
    }

    companion object {
        fun <T> toError(
            context: Context,
            errorType: ErrorType,
            errorBody: String? = null
        ): ApiResponse<T> {
            val apiError = when (errorType) {
                ErrorType.SERVER_ERROR -> {
                    try {
                        val gson = Gson()
                        gson.fromJson(errorBody, ApiError::class.java)
                    } catch (exception: Exception) {
                        Log.e("ApiError", "Failed to parse error into ApiError")
                        exception.printStackTrace()
                        ApiError(context.getString(R.string.unknown_error))
                    }
                }

                ErrorType.NETWORK_ERROR -> {
                    ApiError(context.getString(R.string.network_error))
                }
            }
            return ERROR(errorType, apiError.message)
        }
    }
}