package com.yavin.cashregister.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.yavin.cashregister.R
import com.yavin.cashregister.databinding.SelectTerminalScreenLayoutBinding
import com.yavin.cashregister.service.model.TerminalServiceDTO
import com.yavin.cashregister.view.adapter.TerminalsAdapter
import com.yavin.cashregister.viewmodel.MainViewModel

class SelectTerminalFragment : Fragment(R.layout.select_terminal_screen_layout) {

    private val sharedMainViewModel: MainViewModel by activityViewModels()

    private var _binding: SelectTerminalScreenLayoutBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = SelectTerminalScreenLayoutBinding.bind(view)

        val adapter = TerminalsAdapter { terminalDTO -> adapterOnClick(terminalDTO) }
        binding.terminalsRv.adapter = adapter

        sharedMainViewModel.terminalsData.observe(viewLifecycleOwner) { listOfTerminals ->
            adapter.updateDataProvider(listOfTerminals ?: emptyList())
        }
    }

    private fun adapterOnClick(terminalData: TerminalServiceDTO) {
        println("Connect to the terminal")
        sharedMainViewModel.sendSimplePayment(terminalData.host, "002")
    }

}