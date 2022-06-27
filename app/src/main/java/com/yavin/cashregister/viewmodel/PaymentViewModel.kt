package com.yavin.cashregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yavin.cashregister.network.ApiResponse
import com.yavin.cashregister.model.PaymentScreenUiState
import com.yavin.cashregister.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    application: Application,
    private val transactionRepository: TransactionRepository,
) : AndroidViewModel(application) {
    private val _uiState:MutableStateFlow<PaymentScreenUiState> = MutableStateFlow(PaymentScreenUiState.Loading)
    val uiState: StateFlow<PaymentScreenUiState> = _uiState

    fun makeSimplePayment(hostIp: String, amountCts: String) {
        viewModelScope.launch {

            transactionRepository.makeSimplePayment(hostIp, amountCts)
                .collect { response ->
                    when (response) {
                        is ApiResponse.SUCCESS -> {
                            _uiState.value = PaymentScreenUiState.Success
                        }

                        is ApiResponse.ERROR -> {
                            _uiState.value = PaymentScreenUiState.Error
                        }
                    }
                }
        }
    }


}