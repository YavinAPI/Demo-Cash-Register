package com.yavin.cashregister.view.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.yavin.cashregister.R
import com.yavin.cashregister.databinding.FragmentHomeBinding
import com.yavin.cashregister.service.model.PaymentInitiativeData
import com.yavin.cashregister.viewmodel.HomeViewModel
import com.yavin.cashregister.viewmodel.MainViewModel
import com.yavin.macewindu.logging.ILogger
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    companion object {
        const val TRANSACTION_COUNT = "transactionCount"
    }

    private val logName = this::class.java.simpleName

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val sharedMainViewModel: MainViewModel by activityViewModels()
    private var clearSumAndReferenceOnLeaveFragment: Boolean = false
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var loginConfirmationDialog: BottomSheetDialog

    @Inject
    lateinit var logger: ILogger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        _binding = FragmentHomeBinding.bind(view)

        binding.apply {

            btnDel.setOnClickListener { clickDelButton(sum, btnDel) }

            btnClear.setOnClickListener {
                tmpChangeButtonColor(binding.btnClear)
                homeViewModel.handleUserClearAmount()
            }

            btnPay.setOnClickListener { clickPayButton(btnPay) }

            arrayListOf(
                btn0,
                btn1,
                btn2,
                btn3,
                btn4,
                btn5,
                btn6,
                btn7,
                btn8,
                btn9
            ).forEachIndexed { index, button ->
                button.setOnClickListener {
                    addNumber(sum, index.toString(), it as? Button)
                }
            }

        }

        homeViewModel.validateReferenceLiveData.observe(viewLifecycleOwner) { referenceNullable ->
            referenceNullable?.let { referenceNotNull ->
                if (referenceNotNull.isNotEmpty()) {
                    binding.referenceTextview.text =
                        getString(R.string.reference_output, referenceNotNull)
                } else {
                    binding.referenceTextview.text = referenceNotNull
                }

                binding.addReference.setText(referenceNotNull)
            }
        }

        homeViewModel.sumValueFormattedData.observe(viewLifecycleOwner) {
            it?.let { sumValueFormatted ->
                binding.sum.text = sumValueFormatted
            }
        }

    }

    override fun onStop() {
        if (clearSumAndReferenceOnLeaveFragment) {
            homeViewModel.handleUserClearAmount()
            homeViewModel.setValidateReferenceValue("")
            clearSumAndReferenceOnLeaveFragment = false
        }
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addNumber(text: TextView?, number: String, btn: Button? = null) {
        logger.d("Add Number", number)
        tmpChangeButtonColor(btn)

        if (text == null) {
            return
        }

        homeViewModel.handleUserAddNumber(number.toInt())
    }

    private fun clickPayButton(btn: Button? = null) {
        tmpChangeButtonColor(btn)

        homeViewModel.sumValueFormattedData.value?.let {

            if (homeViewModel.sumValueDouble > 0.0) {

                clearSumAndReferenceOnLeaveFragment = true

                val args = Bundle()
                args.putString("amount", homeViewModel.getSumValueForPaymentActivity())

                val navController = requireActivity().findNavController(R.id.nav_host_fragment)
                navController.navigate(R.id.selectTerminalFragment, args)
            }
        }
    }

    fun clickDelButton(text: TextView?, btn: ImageButton? = null) {
        tmpChangeButtonColor(btn)
        if (text == null) {
            return
        }
        homeViewModel.handleUserDeleteNumber()
    }

    private fun tmpChangeButtonColor(btn: View?) {
        btn?.background = ContextCompat.getDrawable(requireContext(), R.drawable.btn_bg_dark)

        Handler(Looper.getMainLooper()).postDelayed({
            btn?.background = ContextCompat.getDrawable(requireContext(), R.drawable.btn_bg)
        }, 150)
    }
}