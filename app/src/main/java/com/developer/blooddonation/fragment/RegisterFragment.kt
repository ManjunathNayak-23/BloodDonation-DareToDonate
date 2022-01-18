package com.developer.blooddonation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.developer.blooddonation.R
import com.developer.blooddonation.activities.MainActivity
import com.developer.blooddonation.databinding.FragmentRegisterBinding
import com.developer.blooddonation.model.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging


class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var database: DatabaseReference
    private var token: String = "empty"
private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loginNow.setOnClickListener {
            Navigation.findNavController(view)
                .navigate(R.id.action_registerFragment_to_loginFragment)
        }

        binding.registerUser.setOnClickListener {
            validateCredentials()
        }

    }

    private fun validateCredentials() {
        val username = binding.userName.text.toString()
        val email = binding.email.text.toString()
        val password = binding.password.text.toString()
        val bloodGroup = binding.bloodGroup.text.toString()
        val phoneNumber = binding.phoneNumber.text.toString()
        val city = binding.city.text.toString()
        when {
            username.isEmpty() -> {
                binding.userName.error = "Username is Required"
            }
            email.isEmpty() -> {
                binding.email.error = "Email is Required"
            }
            password.isEmpty() -> {
                binding.password.error = "Password is Required"
            }
            bloodGroup.isEmpty() -> {
                binding.bloodGroup.error = "BloodGroup is Required"
            }
            phoneNumber.isEmpty() -> {
                binding.phoneNumber.error = "PhoneNumber is Required"
            }
            city.isEmpty() -> {
                binding.city.error = "City is Required"
            }
            password.length < 6 -> {
                binding.password.error = "Password should more than 6 chars long"
            }
            phoneNumber.length < 10 -> {
                binding.phoneNumber.error = "Enter a valid PhoneNumber"
            }
            else -> {
                auth= FirebaseAuth.getInstance()
                database = Firebase.database.reference
                registerUser(username, email, password, bloodGroup, phoneNumber, city)
            }
        }
    }

    private fun registerUser(
        username: String,
        email: String,
        password: String,
        bloodGroup: String,
        phoneNumber: String,
        city: String
    ) {
        binding.regProgressLayout.visibility = View.VISIBLE
        binding.registerProgressbar.visibility = View.VISIBLE
        binding.infoTxt.visibility = View.VISIBLE


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w("FCM", "Fetching FCM registration token failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new FCM registration token
                    token = task.result.toString()

                  uploadUserToDB(  username,
                      email,

                      bloodGroup,
                      phoneNumber,
                      city,token)
                })

            } else {
                binding.regProgressLayout.visibility = View.INVISIBLE
                binding.registerProgressbar.visibility = View.INVISIBLE
                binding.infoTxt.visibility = View.INVISIBLE
                Toast.makeText(
                    requireContext(),
                    task.exception?.message.toString(),
                    Toast.LENGTH_LONG
                )
                    .show()

            }
        }
    }
    private fun uploadUserToDB(
        username: String,
        email: String,
        bloodGroup: String,
        phoneNumber: String,
        city: String,
        token: String
    ) {

        val uid = auth.currentUser?.uid
        val user =
            User(uid, username, email, bloodGroup, city, phoneNumber, 0, 0, true,token)
        val profileUpdates = userProfileChangeRequest {
            displayName = username
        }
        val userInfo = Firebase.auth.currentUser
        userInfo!!.updateProfile(profileUpdates).addOnCompleteListener { task ->
            if (task.isSuccessful) {

                database.child("users").child(uid.toString()).setValue(user)
                    .addOnCompleteListener { task1 ->
                        if (task.isSuccessful) {
                            startActivity(
                                Intent(
                                    requireActivity(),
                                    MainActivity::class.java
                                )
                            )
                            requireActivity().finishAffinity()
                        } else {
                            binding.regProgressLayout.visibility = View.INVISIBLE
                            binding.registerProgressbar.visibility = View.INVISIBLE
                            binding.infoTxt.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                task1.exception?.message.toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }

            }else{
                Toast.makeText(
                    requireContext(),
                    "Unable to update profile",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}