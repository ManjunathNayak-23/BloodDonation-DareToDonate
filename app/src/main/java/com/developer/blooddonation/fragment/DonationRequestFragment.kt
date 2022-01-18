package com.developer.blooddonation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.developer.blooddonation.R
import com.developer.blooddonation.activities.DonationDetailActivity
import com.developer.blooddonation.adapter.DonationRequestAdapter
import com.developer.blooddonation.model.DonationRequests
import com.developer.blooddonation.notification.FcmNotificationSender
import com.github.ybq.android.spinkit.SpinKitView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class DonationRequestFragment : Fragment(), DonationRequestAdapter.OnClickListener {
    lateinit var donationItemsList: ArrayList<DonationRequests>
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var donationReqAdapter: DonationRequestAdapter
    private lateinit var donationRequestProgressBar: SpinKitView
    private lateinit var auth: FirebaseAuth
    private lateinit var placeHolderImage: ImageView
    private lateinit var placeholderTxt: TextView
    private lateinit var tokenRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_donation_request, container, false)
        placeholderTxt = view.findViewById(R.id.placeholder_txt)
        placeHolderImage = view.findViewById(R.id.placeholder_image)
        database = Firebase.database
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        myRef = database.getReference("UserSpecificDonationRequest").child(uid)
        donationRequestProgressBar = view.findViewById(R.id.donationRequestProgressBar)

        initDonationData()


        setupDonationRequestRecycler(view)
        return view
    }

    private fun initDonationData() {
        donationRequestProgressBar.visibility = View.VISIBLE
        donationItemsList = ArrayList()

        GlobalScope.launch(Dispatchers.IO) {


            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    donationItemsList.clear()
                    if (snapshot.exists()) {
                        for (snapshot1: DataSnapshot in snapshot.children) {
                            val requests = snapshot1.getValue(DonationRequests::class.java)

                            if (requests != null) {
                                donationItemsList.add(requests)
                            }
                        }



                        donationReqAdapter.notifyDataSetChanged()
                        donationRequestProgressBar.visibility = View.INVISIBLE

                        placeHolderImage.visibility = View.INVISIBLE
                        placeholderTxt.visibility = View.INVISIBLE

                    } else {
                        donationRequestProgressBar.visibility = View.INVISIBLE
                        placeHolderImage.visibility = View.VISIBLE
                        placeholderTxt.visibility = View.VISIBLE
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    donationRequestProgressBar.visibility = View.INVISIBLE

                }

            })
        }

    }

    private fun setupDonationRequestRecycler(view: View) {
        val donorRecycler: RecyclerView = view.findViewById(R.id.donorRequestRecycler)
        donorRecycler.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        donationReqAdapter = DonationRequestAdapter(donationItemsList, requireContext(), this)
        donorRecycler.adapter = donationReqAdapter
        donorRecycler.setHasFixedSize(true)


    }

//
//    private fun sendNotification(token: String, name: String) {
//        Toast.makeText(requireContext(), "sent Successfully", Toast.LENGTH_SHORT).show()
//        val notificationSender = FcmNotificationSender(
//            token,
//            "Blood Donation Request Accepted",
//            "$name Accepted Donation Request",
//            requireContext(),
//            requireActivity()
//        )
//        notificationSender.SendNotifications()
//    }

    override fun onClick(uid: String, key: String) {
        val intent = Intent(requireActivity(), DonationDetailActivity::class.java)
        intent.putExtra("uid", uid)
        intent.putExtra("key", key)
        intent.putExtra("userSpecific", true)

        startActivity(intent)
//        Toast.makeText(requireContext(), key, Toast.LENGTH_SHORT).show()
//        var token = "empty"
//        var name = "empty"
//        val auth = FirebaseAuth.getInstance()
//        val myUid = auth.currentUser?.uid.toString()
//        val ref = FirebaseDatabase.getInstance().reference
//
//        ref.child("UserSpecificDonationRequest")
//            .child(myUid).child(key).child("accepted").setValue(true)
//
//        ref.child("users").child(uid).addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//
//                token = snapshot.child("notificationToken").getValue(String::class.java).toString()
//                name = snapshot.child("username").getValue(String::class.java).toString()
//                sendNotification(token, name)
//
//
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })
    }


}