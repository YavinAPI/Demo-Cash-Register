package com.yavin.cashregister.view.callback

import com.yavin.cashregister.model.TerminalServiceDTO

interface IDeviceDiscoveryListener {
    fun onDeviceDiscovered(terminalServiceDTO: TerminalServiceDTO)
    fun onDeviceLost(terminalServiceDTO: TerminalServiceDTO)
}