package com.yavin.cashregister.network

import com.yavin.cashregister.service.model.LocalPaymentResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url


interface YavinApiService {

    // GET http://<IP_LOCALE>:16125/localapi/v1/payment/<MONTANT>/
    //@GET("localapi/v1/payment/{amountCts}")
    @GET
    suspend fun makeSimplePayment(
        @Url url: String
    ): Response<LocalPaymentResponse?>

}