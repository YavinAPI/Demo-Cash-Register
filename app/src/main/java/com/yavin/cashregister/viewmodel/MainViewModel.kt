package com.yavin.cashregister.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.yavin.cashregister.service.model.TerminalServiceDTO

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var _terminalsData:MutableLiveData<List<TerminalServiceDTO>> = MutableLiveData()
    val terminalsData:LiveData<List<TerminalServiceDTO>> = _terminalsData

    fun updateListOfTerminals(newValue:List<TerminalServiceDTO>){
        _terminalsData.postValue(newValue)
    }

}