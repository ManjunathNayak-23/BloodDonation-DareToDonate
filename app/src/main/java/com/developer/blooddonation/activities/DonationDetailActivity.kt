package com.developer.blooddonation.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.developer.blooddonation.databinding.ActivityDonationDetailBinding
import com.developer.blooddonation.model.DonationRequests
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DonationDetailActivity : AppCompatActivity() {
    lateinit var key: String
    lateinit var uid: String
     var fromUserSpecific=false
    var number = ""
    lateinit var binding: ActivityDonationDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDonationDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        uid = intent.getStringExtra("uid").toString()
        key = intent.getStringExtra("key").toString()
        fromUserSpecific = intent.getBooleanExtra("userSpecific", false)
        if (fromUserSpecific) {
            initDataFromUser()

        } else {
            initData()
        }


        binding.calllBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
            startActivity(intent)
        }
    }

    private fun initDataFromUser() {
        val uid=FirebaseAuth.getInstance().currentUser?.uid.toString()

        val ref = FirebaseDatabase.getInstance().reference
        ref.child("UserSpecificDonationRequest").child(uid).child(key)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val model = snapshot.getValue(DonationRequests::class.java)
                    binding.txtDdBloodGroup.text = model?.bloodRequest
                    binding.txtDdHospitalName.text = model?.location
                    binding.txtDdName.text = model?.name
                    binding.txtDdNote.text = model?.note
                    number = model?.mobile.toString()
                    binding.txtDdTime.text = TimeAgo.using(model?.timeStamp?.toLong()!!)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    private fun initData() {

        val ref = FirebaseDatabase.getInstance().reference
        ref.child("DonationRequest").child(key)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val model = snapshot.getValue(DonationRequests::class.java)
                    binding.txtDdBloodGroup.text = model?.bloodRequest
                    binding.txtDdHospitalName.text = model?.location
                    binding.txtDdName.text = model?.name
                    binding.txtDdNote.text = model?.note
                    number = model?.mobile.toString()
                    binding.txtDdTime.text = TimeAgo.using(model?.timeStamp?.toLong()!!)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }
}