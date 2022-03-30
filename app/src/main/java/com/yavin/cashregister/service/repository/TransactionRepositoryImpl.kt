package com.yavin.cashregister.service.repository

import android.content.Context
import com.yavin.cashregister.network.ApiResponse
import com.yavin.cashregister.network.YavinApiService
import com.yavin.cashregister.service.model.LocalPaymentResponse
import com.yavin.macewindu.logging.ILogger
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepositoryImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val logger: ILogger,
    private val yavinApi: YavinApiService,
) : TransactionRepository {

    private val logName = this::class.java.simpleName

    override suspend fun makeSimplePayment(
        hostIp: String,
        amountCts: String
    ): Flow<ApiResponse<LocalPaymentResponse>> {
        return flow {
            try {
                val requestURL = "http://${hostIp}:16125/localapi/v1/payment/$amountCts/"
                val response = yavinApi.makeSimplePayment(requestURL)
                val paymentSucceeded = response.body()?.status == "ok"
                val responseBody = response.body()

                if (response.isSuccessful) {

                    if (paymentSucceeded && responseBody != null) {
                        emit(ApiResponse.SUCCESS(responseBody))
                    } else {
                        emit(
                            ApiResponse.toError(
                                context,
                                ApiResponse.ErrorType.SERVER_ERROR,
                                "payment failed(status=ko)"
                            )
                        )
                    }

                } else {
                    logger.d(
                        logName,
                        "payment failed: ${response.errorBody()?.string()}"
                    )
                    emit(
                        ApiResponse.toError(
                            context,
                            ApiResponse.ErrorType.SERVER_ERROR,
                            response.errorBody()?.string()
                        )
                    )
                }

            } catch (exception: Exception) {
                logger.d(logName, "payment failed due to a network error")
                exception.printStackTrace()
                emit(ApiResponse.toError(context, ApiResponse.ErrorType.NETWORK_ERROR))
            }
        }.flowOn(Dispatchers.IO)
    }


}