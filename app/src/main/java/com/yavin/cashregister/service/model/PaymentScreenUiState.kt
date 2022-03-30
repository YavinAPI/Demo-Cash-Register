package com.yavin.cashregister.service.model

sealed class PaymentScreenUiState {
    object Loading : PaymentScreenUiState()
    object Success : PaymentScreenUiState()
    object Error : PaymentScreenUiState()
}
