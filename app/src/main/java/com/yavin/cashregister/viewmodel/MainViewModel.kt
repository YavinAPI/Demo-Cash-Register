package com.yavin.cashregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.yavin.cashregister.network.ApiResponse
import com.yavin.cashregister.service.model.TerminalServiceDTO
import com.yavin.cashregister.service.repository.TransactionRepository
import com.yavin.macewindu.logging.ILogger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val logger: ILogger,
    application: Application,
    private val transactionRepository: TransactionRepository,
) : AndroidViewModel(application) {

    private var _terminalsData: MutableLiveData<List<TerminalServiceDTO>> = MutableLiveData()
    val terminalsData: LiveData<List<TerminalServiceDTO>> = _terminalsData

    private val logName = this::class.java.simpleName

    fun updateListOfTerminals(newValue: List<TerminalServiceDTO>) {
        _terminalsData.postValue(newValue)
    }

    fun sendSimplePayment(hostIp: String, amountCts: String) {
        viewModelScope.launch {
            transactionRepository.makeSimplePayment(hostIp, amountCts).collect { response ->
                when (response) {
                    is ApiResponse.SUCCESS -> {
                        logger.d(logName, "SUCCESS")
                    }

                    is ApiResponse.ERROR -> {
                        logger.d(logName, response.message.toString())
                    }

                }
            }
        }

    }

}