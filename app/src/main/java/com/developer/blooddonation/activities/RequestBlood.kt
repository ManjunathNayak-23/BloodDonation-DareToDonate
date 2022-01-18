package com.developer.blooddonation.activities

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.developer.blooddonation.R
import com.developer.blooddonation.databinding.ActivityRequestBloodBinding
import com.developer.blooddonation.model.DonationRequests
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RequestBlood : AppCompatActivity() {
    private lateinit var binding: ActivityRequestBloodBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private var uid: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestBloodBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uid = intent.getStringExtra("uidOfRequested").toString()

        binding.requestBlood.setOnClickListener {
            validateData()
        }
    }

    private fun validateData() {
        val city = binding.etCity.text.toString()
        val hospital = binding.etHospital.text.toString()
        val bloodType = binding.etBloodType.text.toString()
        val mobile = binding.etMobile.text.toString()
        val note = binding.etNote.text.toString()

        when {
            city.isEmpty() -> {
                binding.etCity.error = "City is Required"
            }
            hospital.isEmpty() -> {
                binding.etHospital.error = "Hospital Name is Required"
            }
            bloodType.isEmpty() -> {
                binding.etBloodType.error = "Blood Type is Required"
            }
            mobile.isEmpty() -> {
                binding.etMobile.error = "Mobile Number is Required"
            }
            else -> {
                if (uid.length > 4) {

                    updateToUserDB(city, hospital, bloodType, mobile, note, uid)
                } else {
                    updateToDataBase(city, hospital, bloodType, mobile, note)
                }

            }
        }
    }

    private fun updateToUserDB(
        city: String,
        hospital: String,
        bloodType: String,
        mobile: String,
        note: String,
        uidOfRequested: String
    ) {
        binding.regProgressLayout.visibility = View.VISIBLE
        binding.registerProgressbar.visibility = View.VISIBLE
        binding.infoTxt.visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        val name = auth.currentUser?.displayName.toString()
        ref = FirebaseDatabase.getInstance().reference
        val location = "$hospital, $city"
        val time = System.currentTimeMillis().toString()
        val key = ref.push().key.toString()


        val request =
            DonationRequests(bloodType, uid, location, name, time, mobile, note, false, key)


        ref.child("UserSpecificDonationRequest").child(uidOfRequested).child(key).setValue(request)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.regProgressLayout.visibility = View.INVISIBLE
                    binding.registerProgressbar.visibility = View.INVISIBLE
                    binding.infoTxt.visibility = View.INVISIBLE

                    val dialog = Dialog(this)
                    dialog.setContentView(R.layout.request_successfull_layout)
                    dialog.window?.setBackgroundDrawable(
                        ContextCompat.getDrawable(
                            this,
                            R.drawable.dialog_background
                        )
                    )
                    dialog.window?.setLayout(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                    dialog.window?.attributes?.windowAnimations = R.style.animation
                    dialog.setCancelable(false)
                    val nxtBtn = dialog.findViewById<ImageButton>(R.id.nxtBtn)
                    nxtBtn.setOnClickListener {
                        dialog.dismiss()
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    }
                    dialog.show()


                } else {
                    binding.regProgressLayout.visibility = View.INVISIBLE
                    binding.registerProgressbar.visibility = View.INVISIBLE
                    binding.infoTxt.visibility = View.INVISIBLE

                    Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_LONG).show()
                }
            }


    }

    private fun updateToDataBase(
        city: String,
        hospital: String,
        bloodType: String,
        mobile: String,
        note: String
    ) {

        binding.regProgressLayout.visibility = View.VISIBLE
        binding.registerProgressbar.visibility = View.VISIBLE
        binding.infoTxt.visibility = View.VISIBLE
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        val name = auth.currentUser?.displayName.toString()
        ref = FirebaseDatabase.getInstance().reference
        val location = "$hospital, $city"
        val time = System.currentTimeMillis().toString()
        val key = ref.push().key.toString()


        val request =
            DonationRequests(bloodType, uid, location, name, time, mobile, note, false, key)


        ref.child("DonationRequest").child(key).setValue(request).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                binding.regProgressLayout.visibility = View.INVISIBLE
                binding.registerProgressbar.visibility = View.INVISIBLE
                binding.infoTxt.visibility = View.INVISIBLE

                val dialog = Dialog(this)
                dialog.setContentView(R.layout.request_successfull_layout)
                dialog.window?.setBackgroundDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.dialog_background
                    )
                )
                dialog.window?.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
                dialog.window?.attributes?.windowAnimations = R.style.animation
                dialog.setCancelable(false)
                val nxtBtn = dialog.findViewById<ImageButton>(R.id.nxtBtn)
                nxtBtn.setOnClickListener {
                    dialog.dismiss()
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }
                dialog.show()


            } else {
                binding.regProgressLayout.visibility = View.INVISIBLE
                binding.registerProgressbar.visibility = View.INVISIBLE
                binding.infoTxt.visibility = View.INVISIBLE

                Toast.makeText(this, "Some Error Occurred", Toast.LENGTH_LONG).show()
            }
        }


    }
}