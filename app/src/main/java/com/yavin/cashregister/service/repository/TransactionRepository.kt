package com.yavin.cashregister.service.repository

import com.yavin.cashregister.network.ApiResponse
import com.yavin.cashregister.service.model.LocalPaymentResponse
import kotlinx.coroutines.flow.Flow


interface TransactionRepository {

    suspend fun makeSimplePayment(hostIp: String, amountCts: String): Flow<ApiResponse<LocalPaymentResponse>>

}