package com.yavin.cashregister.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.yavin.cashregister.R
import com.yavin.cashregister.databinding.FragmentPaymentBinding
import com.yavin.cashregister.service.model.PaymentInitiativeData
import com.yavin.cashregister.service.model.PaymentScreenUiState
import com.yavin.cashregister.viewmodel.PaymentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaymentFragment : Fragment(R.layout.fragment_payment) {

    private var _binding:FragmentPaymentBinding?=null
    private val binding
        get() = _binding!!

    private val paymentViewModel: PaymentViewModel by viewModels()
    private var initParams: PaymentInitiativeData? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initParams = arguments?.getSerializable(INIT_PARAMS_OBJECT_NAME) as? PaymentInitiativeData
        _binding = FragmentPaymentBinding.bind(view)

        binding.paymentFragmentNewPaymentBtn.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.homeFragment)
        }

        observeState()

        initParams?.let {
            paymentViewModel.makeSimplePayment(it.hostIp, it.amountCts)
        }

    }

    private fun observeState(){
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                paymentViewModel.uiState.collect { uiState ->

                    binding.paymentProgressBarContainer.visibility = View.GONE
                    binding.paymentFailedContainer.visibility = View.GONE
                    binding.paymentSucceededContainer.visibility = View.GONE
                    binding.paymentFragmentNewPaymentBtn.visibility = View.GONE

                    when(uiState){
                        PaymentScreenUiState.Loading ->{
                            binding.paymentProgressBarContainer.visibility = View.VISIBLE
                        }

                        PaymentScreenUiState.Success -> {
                            binding.paymentSucceededContainer.visibility = View.VISIBLE
                            binding.paymentFragmentNewPaymentBtn.visibility = View.VISIBLE
                        }

                        PaymentScreenUiState.Error -> {
                            binding.paymentFailedContainer.visibility = View.VISIBLE
                            binding.paymentFragmentNewPaymentBtn.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val INIT_PARAMS_OBJECT_NAME = "init_params_obj_name"
    }

}