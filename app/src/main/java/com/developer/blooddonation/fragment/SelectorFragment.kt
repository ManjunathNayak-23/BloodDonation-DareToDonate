package com.developer.blooddonation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.blooddonation.R
import com.developer.blooddonation.databinding.FragmentSelectorBinding

class SelectorFragment : Fragment(R.layout.fragment_selector) {
    private var _binding: FragmentSelectorBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

         _binding = FragmentSelectorBinding.inflate(inflater, container, false)




        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginScreenBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_selectorFragment_to_loginFragment)
        }

        binding.registerScreenBtn.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_selectorFragment_to_registerFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}