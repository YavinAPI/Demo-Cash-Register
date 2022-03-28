package com.yavin.cashregister.network

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import com.yavin.cashregister.service.model.TerminalServiceDTO
import com.yavin.cashregister.view.callback.IDeviceDiscoveryListener


class NsdHelper {

    private val logName = this::class.java.simpleName

    companion object {

        const val SERVICE_TYPE = "_http._tcp."
        const val PORT = 16125

        @Volatile
        private var instance: NsdHelper? = null

        fun getInstance(): NsdHelper {
            return instance ?: synchronized(this) {
                instance ?: NsdHelper().also { instance = it }
            }
        }
    }

    private var nsdManager: NsdManager? = null
    private var mServiceName: String = "yavin"
    private var mService: NsdServiceInfo? = null
    private var listeners: ArrayList<IDeviceDiscoveryListener> = arrayListOf()
    private var resolvedTerminals:ArrayList<TerminalServiceDTO> = arrayListOf()


    private val resolveListener = object : NsdManager.ResolveListener {
        override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
            Log.e(logName, "Resolve failed: $errorCode")
        }

        override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
            Log.e(logName, "Resolve Succeeded. $serviceInfo")

            serviceInfo.host.hostAddress?.let { hostAddress ->
                addTerminalToList(TerminalServiceDTO(hostAddress, serviceInfo.port, serviceInfo.serviceName))
            }

        }
    }

    private fun addTerminalToList(terminalDTO:TerminalServiceDTO){
        if(!resolvedTerminals.contains(terminalDTO)){
            resolvedTerminals.add(terminalDTO)
            notifyListeners()
        }
    }

    private fun notifyListeners() {
        listeners.forEach {
            it.onDeviceListChanged(resolvedTerminals)
        }
    }

    private val discoveryListener = object : NsdManager.DiscoveryListener {

        override fun onDiscoveryStarted(regType: String) {
            Log.d(logName, "Service discovery started")
        }

        override fun onServiceFound(service: NsdServiceInfo) {
            Log.d(logName, "Service discovery success$service")
            when {
                service.serviceType != SERVICE_TYPE -> {
                    Log.d(logName, "Unknown Service Type: ${service.serviceType}")
                }

                service.serviceName.contains(mServiceName) -> {
                    nsdManager?.resolveService(service, resolveListener)
                }
            }

        }

        override fun onServiceLost(service: NsdServiceInfo?) {
            Log.e(logName, "service lost: $service")
        }

        override fun onDiscoveryStopped(serviceType: String?) {
            Log.i(logName, "Discovery stopped: $serviceType")
        }

        override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(logName, "Discovery failed: Error code:$errorCode")
            nsdManager?.stopServiceDiscovery(this)
        }

        override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
            Log.e(logName, "Discovery failed: Error code:$errorCode")
            nsdManager?.stopServiceDiscovery(this)
        }

    }


    fun startDiscover(appContext: Context) {
        try {
            nsdManager = (appContext.getSystemService(Context.NSD_SERVICE) as NsdManager).apply {
                discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
            }
        }catch (exception:Exception){
            exception.printStackTrace()
        }
    }

    fun stopDiscover() {
        try {
            nsdManager?.stopServiceDiscovery(discoveryListener)
            resolvedTerminals = arrayListOf()
            notifyListeners()
        }catch (exception:Exception){
            exception.printStackTrace()
        }
    }

    fun addListener(newListener:IDeviceDiscoveryListener){
        if (!listeners.contains(newListener)) {
            listeners.add(newListener)
            newListener.onDeviceListChanged(resolvedTerminals)
        }
    }

    fun removeListener(listener:IDeviceDiscoveryListener){
        if(listeners.contains(listener)){
            listeners.remove(listener)
        }
    }

}