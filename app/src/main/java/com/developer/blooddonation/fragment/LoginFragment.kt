package com.developer.blooddonation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.blooddonation.R
import com.developer.blooddonation.activities.MainActivity
import com.developer.blooddonation.databinding.FragmentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.registerNow.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.loginBtn.setOnClickListener {

            validateUserCredentials()
        }


    }

    private fun validateUserCredentials() {
        val email = binding.loginEmail.text.toString()
        val password = binding.loginPassword.text.toString()
        when {
            email.isEmpty() -> {
                binding.loginEmail.error = "Email is Required"
            }
            password.isEmpty() -> {
                binding.loginPassword.error = "Password is Required"
            }
            password.length < 6 -> {
                binding.loginPassword.error = "Password cannot be less than 6 characters long"
            }
            else -> {
                loginUser(email, password)
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        binding.loginProgressLayout.visibility = View.VISIBLE
        binding.loginProgressbar.visibility = View.VISIBLE
        binding.logInfoTxt.visibility = View.VISIBLE
        val auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                startActivity(Intent(requireActivity(), MainActivity::class.java))
                finishAffinity(requireActivity())

            }else{
                Log.d("error",task.exception?.message.toString())
                Toast.makeText(requireActivity(),task.exception?.message.toString(),Toast.LENGTH_LONG).show()
                binding.loginProgressLayout.visibility = View.INVISIBLE
                binding.loginProgressbar.visibility = View.INVISIBLE
                binding.logInfoTxt.visibility = View.INVISIBLE

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}