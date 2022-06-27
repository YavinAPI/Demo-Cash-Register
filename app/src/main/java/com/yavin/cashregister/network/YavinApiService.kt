package com.yavin.cashregister.network

import com.yavin.cashregister.model.LocalPaymentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface YavinApiService {

    @GET
    suspend fun makeSimplePayment(
        @Url url: String
    ): Response<LocalPaymentResponse?>

}