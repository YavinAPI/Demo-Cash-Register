package com.yavin.cashregister.service.model

import com.google.gson.annotations.SerializedName

data class LocalPaymentResponse(
    @SerializedName("created_at")
    var createAt: String? = null,
    @SerializedName("amount")
    var amount: Int? = null,
    @SerializedName("gift_amount")
    var giftAmount: Int? = null,
    @SerializedName("status")
    var status: String? = null,
    @SerializedName("trs_id")
    var trs_id: String? = null,
    @SerializedName("scheme")
    var scheme: String? = null,
    @SerializedName("scheme_group")
    var scheme_group: String? = null,
    @SerializedName("type")
    var type: String? = null,
    @SerializedName("reference")
    var reference: String? = null,
    @SerializedName("phone_number")
    var phoneNumber: String? = null,
    @SerializedName("email")
    var email: String? = null,
)