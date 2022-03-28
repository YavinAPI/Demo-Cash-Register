package com.yavin.cashregister.view.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.yavin.cashregister.R
import com.yavin.cashregister.databinding.MainMenuFragmentLayoutBinding

class MainMenuFragment : Fragment(R.layout.main_menu_fragment_layout) {

    private var _binding: MainMenuFragmentLayoutBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = MainMenuFragmentLayoutBinding.bind(view)
        binding.mainNewPaymentBtn.setOnClickListener {
            val navController = requireActivity().findNavController(R.id.nav_host_fragment)
            navController.navigate(R.id.homeFragment)
        }
    }


}