package com.yavin.cashregister.view.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.yavin.cashregister.R
import com.yavin.cashregister.network.NsdHelper
import com.yavin.cashregister.service.model.TerminalServiceDTO
import com.yavin.cashregister.view.callback.IDeviceDiscoveryListener
import com.yavin.cashregister.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IDeviceDiscoveryListener {

    private val sharedMainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        NsdHelper.getInstance().addListener(this)
        NsdHelper.getInstance().startDiscover(applicationContext)
    }

    override fun onPause() {
        NsdHelper.getInstance().removeListener(this)
        NsdHelper.getInstance().stopDiscover()
        super.onPause()
    }

    override fun onDestroy() {
        NsdHelper.getInstance().removeListener(this)
        NsdHelper.getInstance().stopDiscover()
        super.onDestroy()
    }

    override fun onDeviceListChanged(devices: List<TerminalServiceDTO>) {
        sharedMainViewModel.updateListOfTerminals(devices)
    }
}