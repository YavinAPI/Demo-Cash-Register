package com.yavin.cashregister.model

sealed class PaymentScreenUiState {
    object Loading : PaymentScreenUiState()
    object Success : PaymentScreenUiState()
    object Error : PaymentScreenUiState()
}
