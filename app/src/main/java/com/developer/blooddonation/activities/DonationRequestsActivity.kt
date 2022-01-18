package com.developer.blooddonation.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.developer.blooddonation.R
import com.developer.blooddonation.adapter.DonationRequestAdapter
import com.developer.blooddonation.model.DonationRequests
import com.developer.blooddonation.notification.FcmNotificationSender
import com.github.ybq.android.spinkit.SpinKitView
import com.google.firebase.database.*

class DonationRequestsActivity : AppCompatActivity(), DonationRequestAdapter.OnClickListener {
    private lateinit var donationRequestRecycler: RecyclerView
    private lateinit var list: ArrayList<DonationRequests>
    private lateinit var ref: DatabaseReference
    private lateinit var adapter: DonationRequestAdapter
    private lateinit var progressBar: SpinKitView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_donation_request)
        donationRequestRecycler = findViewById(R.id.donationRequestRecycler)
        progressBar = findViewById(R.id.progressBar)
        list = ArrayList<DonationRequests>()
        adapter = DonationRequestAdapter(list, this, this)
        donationRequestRecycler.adapter = adapter
        initData()


    }

    private fun initData() {
        progressBar.visibility = View.VISIBLE


        list.clear()
        ref = FirebaseDatabase.getInstance().reference
        ref.child("DonationRequest").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (snapshot1: DataSnapshot in snapshot.children) {
                        val requests = snapshot1.getValue(DonationRequests::class.java)

                        if (requests != null) {
                            list.add(requests)
                        }
                    }
                    adapter.notifyDataSetChanged()
                    progressBar.visibility = View.INVISIBLE


                } else {
                    progressBar.visibility = View.INVISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@DonationRequestsActivity, error.message, Toast.LENGTH_SHORT)
                    .show()
                progressBar.visibility = View.INVISIBLE
            }


        })
    }

    private fun sendNotification(token: String, name: String) {
        Toast.makeText(applicationContext, "Sent Successfully", Toast.LENGTH_SHORT).show()
        val notificationSender = FcmNotificationSender(
            token,
            "Blood Donation Request Accepted",
            "$name Accepted Donation Request",
            applicationContext,
            this
        )
        notificationSender.SendNotifications()
    }


    override fun onClick(uid: String, key: String) {

        var token = "empty"
        var name = "empty"
        val ref = FirebaseDatabase.getInstance().reference
        ref.child("users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                token = snapshot.child("notificationToken").getValue(String::class.java).toString()
                name = snapshot.child("username").getValue(String::class.java).toString()
                sendNotification(token, name)


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}