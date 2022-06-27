package com.yavin.cashregister.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.yavin.cashregister.R
import com.yavin.cashregister.model.TerminalServiceDTO
import com.yavin.cashregister.network.NsdHelper
import com.yavin.cashregister.view.callback.IDeviceDiscoveryListener
import com.yavin.cashregister.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IDeviceDiscoveryListener {

    @Inject
    lateinit var networkServiceDiscoveryHelper: NsdHelper

    private val sharedMainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        networkServiceDiscoveryHelper.startDiscovery(this)
    }

    override fun onPause() {
        networkServiceDiscoveryHelper.stopDiscovery()
        super.onPause()
    }

    override fun onDeviceDiscovered(terminalServiceDTO: TerminalServiceDTO) {
        lifecycleScope.launch(Dispatchers.Main) {
            sharedMainViewModel.onDeviceDiscovered(terminalServiceDTO)
        }
    }

    override fun onDeviceLost(terminalServiceDTO: TerminalServiceDTO) {
        lifecycleScope.launch(Dispatchers.Main) {
            sharedMainViewModel.onDeviceLost(terminalServiceDTO)
        }
    }
}