package com.developer.blooddonation.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.developer.blooddonation.adapter.MyRequestsAdapter
import com.developer.blooddonation.databinding.ActivityRequestedBinding
import com.developer.blooddonation.model.MyRequestModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class RequestedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRequestedBinding
    private lateinit var adapter: MyRequestsAdapter
    private lateinit var list: ArrayList<MyRequestModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = ArrayList()
        adapter = MyRequestsAdapter(list)
        binding.requestedRecyclerview.adapter = adapter
        initData()

    }

    private fun initData() {
        binding.ProgressLayout.visibility = View.VISIBLE
        binding.Progressbar.visibility = View.VISIBLE
        binding.InfoTxt.visibility = View.VISIBLE
        val auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("UserSpecificDonationRequest").child(uid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {


                        for (snapshot1: DataSnapshot in snapshot.children) {
                            val model = snapshot1.getValue(MyRequestModel::class.java)
                            if (model != null) {
                                list.add(model)
                            }
                        }

                        adapter.notifyDataSetChanged()
                        binding.ProgressLayout.visibility = View.INVISIBLE
                        binding.Progressbar.visibility = View.INVISIBLE
                        binding.InfoTxt.visibility = View.INVISIBLE
                    } else {
                        binding.ProgressLayout.visibility = View.INVISIBLE
                        binding.Progressbar.visibility = View.INVISIBLE
                        binding.InfoTxt.visibility = View.INVISIBLE
                        binding.emptyImg.visibility = View.VISIBLE
                        binding.emptyTxt.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    binding.ProgressLayout.visibility = View.INVISIBLE
                    binding.Progressbar.visibility = View.INVISIBLE
                    binding.InfoTxt.visibility = View.INVISIBLE
                    binding.emptyImg.visibility = View.VISIBLE
                    binding.emptyTxt.visibility = View.VISIBLE
                    Toast.makeText(this@RequestedActivity, "Some error occured", Toast.LENGTH_SHORT)
                        .show()
                }

            })
    }
}