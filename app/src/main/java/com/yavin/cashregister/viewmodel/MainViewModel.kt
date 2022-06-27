package com.yavin.cashregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yavin.cashregister.model.TerminalServiceDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    application: Application,
) : AndroidViewModel(application) {

    private var _terminalsData: MutableLiveData<List<TerminalServiceDTO>> = MutableLiveData()
    val terminalsData: LiveData<List<TerminalServiceDTO>> = _terminalsData

    private val terminals = mutableListOf<TerminalServiceDTO>()

    private fun isAdded(terminalDTO: TerminalServiceDTO): Boolean {
        return terminals.find {
            it.name == terminalDTO.name
        } != null
    }

    fun onDeviceDiscovered(terminalServiceDTO: TerminalServiceDTO) {
        if (!isAdded(terminalServiceDTO)) {
            terminals.add(terminalServiceDTO)
            _terminalsData.postValue(terminals)
        }
    }

    fun onDeviceLost(terminalServiceDTO: TerminalServiceDTO) {
        if (isAdded(terminalServiceDTO)) {
            terminals.remove(terminalServiceDTO)
            _terminalsData.postValue(terminals)
        }
    }
}