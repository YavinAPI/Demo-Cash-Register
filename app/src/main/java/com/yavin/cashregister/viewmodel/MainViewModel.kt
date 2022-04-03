package com.yavin.cashregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yavin.cashregister.service.model.TerminalServiceDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    private var _terminalsData: MutableLiveData<List<TerminalServiceDTO>> = MutableLiveData()
    val terminalsData: LiveData<List<TerminalServiceDTO>> = _terminalsData

    private val logName = this::class.java.simpleName

    fun updateListOfTerminals(newValue: List<TerminalServiceDTO>) {
        _terminalsData.postValue(newValue)
    }

}