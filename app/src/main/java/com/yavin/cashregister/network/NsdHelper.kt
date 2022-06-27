package com.yavin.cashregister.network

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import com.yavin.cashregister.model.TerminalServiceDTO
import com.yavin.cashregister.view.callback.IDeviceDiscoveryListener
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NsdHelper @Inject constructor(
    @ApplicationContext context: Context
) {

    private val logName = this::class.java.simpleName

    companion object {
        private const val SERVICE_TYPE = "_http._tcp."

        private const val SERVICE_NAME_PREFIX: String = "yavin"
    }

    private var nsdManager: NsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager

    private var discoveryListener: NsdManager.DiscoveryListener? = null
    private var resolveListener: NsdManager.ResolveListener? = null
    private var deviceDiscoveryListener: IDeviceDiscoveryListener? = null

    private fun initializeDiscoveryListener() {
        discoveryListener = object : NsdManager.DiscoveryListener {
            override fun onDiscoveryStarted(regType: String) {
                Log.d(logName, "Service discovery started")
            }

            override fun onServiceFound(service: NsdServiceInfo) {
                Log.d(logName, "Service discovery success$service")
                when {
                    service.serviceType != SERVICE_TYPE -> {
                        Log.d(logName, "Unknown Service Type: ${service.serviceType}")
                    }

                    service.serviceName.contains(SERVICE_NAME_PREFIX) -> {
                        nsdManager.resolveService(service, resolveListener)
                    }
                }
            }

            override fun onServiceLost(service: NsdServiceInfo?) {
                Log.e(logName, "service lost: $service")
                service?.let { serviceInfo ->
                    serviceInfo.host?.hostAddress?.let { hostAddress ->
                        deviceDiscoveryListener?.onDeviceLost(
                            TerminalServiceDTO(
                                hostAddress,
                                serviceInfo.port,
                                serviceInfo.serviceName
                            )
                        )
                    }
                }
            }

            override fun onDiscoveryStopped(serviceType: String?) {
                Log.i(logName, "Discovery stopped: $serviceType")
            }

            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e(logName, "Discovery failed: Error code:$errorCode")
                nsdManager.stopServiceDiscovery(this)
            }

            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e(logName, "Discovery failed: Error code:$errorCode")
                nsdManager.stopServiceDiscovery(this)
            }
        }
    }

    private fun initializeResolveListener() {
        resolveListener = object : NsdManager.ResolveListener {
            override fun onResolveFailed(serviceInfo: NsdServiceInfo, errorCode: Int) {
                Log.e(logName, "Resolve failed: $errorCode")
            }

            override fun onServiceResolved(serviceInfo: NsdServiceInfo) {
                Log.e(logName, "Resolve Succeeded. $serviceInfo")

                serviceInfo.host.hostAddress?.let { hostAddress ->
                    deviceDiscoveryListener?.onDeviceDiscovered(
                        TerminalServiceDTO(
                            hostAddress,
                            serviceInfo.port,
                            serviceInfo.serviceName
                        )
                    )
                }
            }
        }
    }

    fun startDiscovery(listener: IDeviceDiscoveryListener) {
        try {
            stopDiscovery()

            deviceDiscoveryListener = listener
            initializeResolveListener()
            initializeDiscoveryListener()

            nsdManager.discoverServices(
                SERVICE_TYPE,
                NsdManager.PROTOCOL_DNS_SD,
                discoveryListener
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    fun stopDiscovery() {
        try {
            if (discoveryListener != null) {
                nsdManager.stopServiceDiscovery(discoveryListener)
            }
            discoveryListener = null
            resolveListener = null
            deviceDiscoveryListener = null
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}