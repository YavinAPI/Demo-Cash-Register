package com.yavin.cashregister.repository

import com.yavin.cashregister.network.ApiResponse
import com.yavin.cashregister.model.LocalPaymentResponse
import kotlinx.coroutines.flow.Flow


interface TransactionRepository {

    suspend fun makeSimplePayment(
        hostIp: String,
        amountCts: String
    ): Flow<ApiResponse<LocalPaymentResponse>>

}