package com.yavin.cashregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.yavin.cashregister.service.repository.TransactionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    application: Application,
    private val transactionRepository: TransactionRepository,
) : AndroidViewModel(application) {

}