package com.yavin.cashregister.service.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiError(
        @SerializedName("message")
        val message: String
) : Serializable