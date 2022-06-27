package com.yavin.cashregister.model

import com.google.gson.annotations.SerializedName

data class Customer(
    @SerializedName("email")
    var email: String?,
    @SerializedName("firstName")
    var firstName: String?,
    @SerializedName("lastName")
    var lastName: String?,
    @SerializedName("phone")
    var phone: String?
)