package com.yavin.cashregister.view.callback

import com.yavin.cashregister.service.model.TerminalServiceDTO

interface IDeviceDiscoveryListener {
    fun onDeviceListChanged(devices: List<TerminalServiceDTO>)
}