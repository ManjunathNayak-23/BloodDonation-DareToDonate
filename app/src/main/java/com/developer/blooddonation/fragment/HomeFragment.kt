package com.developer.blooddonation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.developer.blooddonation.R
import com.developer.blooddonation.activities.*
import com.developer.blooddonation.adapter.DonationRequestAdapter
import com.developer.blooddonation.adapter.MainGridAdapter
import com.developer.blooddonation.model.DonationRequests
import com.developer.blooddonation.model.MainMenuModel
import com.developer.blooddonation.notification.FcmNotificationSender
import com.github.ybq.android.spinkit.SpinKitView
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class HomeFragment : Fragment(), MainGridAdapter.onItemClickListener,
    DonationRequestAdapter.OnClickListener {
    lateinit var menuItemsList: ArrayList<MainMenuModel>
    lateinit var donationItemsList: ArrayList<DonationRequests>
    lateinit var database: FirebaseDatabase
    lateinit var myRef: DatabaseReference
    lateinit var donationReqAdapter: DonationRequestAdapter
    private lateinit var homeProgressBar: SpinKitView
    private lateinit var progress_layout: RelativeLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        database = Firebase.database
        myRef = database.getReference("DonationRequest")
        homeProgressBar = view.findViewById(R.id.homeProgressBar)
        progress_layout = view.findViewById(R.id.progress_layout)
        initMenuData()
        setupImageSlider(view)


        setupMainMenu(view)
        initDonationData()
        setupDonationRequestRecycler(view)
        return view
    }

    private fun initDonationData() {
        homeProgressBar.visibility = View.VISIBLE
        progress_layout.visibility = View.VISIBLE
        donationItemsList = ArrayList()


        myRef.limitToLast(4).addValueEventListener(object : ValueEventListener {
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
                    homeProgressBar.visibility = View.INVISIBLE
                    progress_layout.visibility = View.INVISIBLE


                } else {
                    homeProgressBar.visibility = View.INVISIBLE
                    progress_layout.visibility = View.INVISIBLE

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
                homeProgressBar.visibility = View.INVISIBLE
                progress_layout.visibility = View.INVISIBLE
            }

        })

    }

    private fun setupDonationRequestRecycler(view: View) {
        val donorRecycler: RecyclerView = view.findViewById(R.id.donorRequest)
        val layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, true)
        donationReqAdapter = DonationRequestAdapter(donationItemsList, requireContext(), this)
        donorRecycler.layoutManager = layoutManager
        donorRecycler.adapter = donationReqAdapter


    }

    private fun initMenuData() {
        menuItemsList = ArrayList()
        menuItemsList.add(MainMenuModel(R.drawable.ic_find_donors, "Find Donors"))
        menuItemsList.add(MainMenuModel(R.drawable.donates, "Donates"))
        menuItemsList.add(MainMenuModel(R.drawable.ic_order_blood, "Blood Banks"))
        menuItemsList.add(MainMenuModel(R.drawable.ic_assistant, "Assistant"))
        menuItemsList.add(MainMenuModel(R.drawable.ic_campains, "Campaign"))
        menuItemsList.add(MainMenuModel(R.drawable.ic_add_blood, "Request Blood"))

    }

    private fun setupMainMenu(view: View) {
        val menuRecycler: RecyclerView = view.findViewById(R.id.menuRecycler)
        menuRecycler.layoutManager = GridLayoutManager(requireContext(), 3)
        menuRecycler.adapter = MainGridAdapter(menuItemsList, requireContext(), this)
        menuRecycler.setHasFixedSize(true)
    }

    fun setupImageSlider(view: View) {
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.slider_first))
        imageList.add(SlideModel(R.drawable.slider_second))
        imageList.add(SlideModel(R.drawable.slider_third))

        val imageSlider = view.findViewById<ImageSlider>(R.id.image_slider)

        imageSlider.setImageList(imageList, scaleType = ScaleTypes.CENTER_INSIDE)


    }

    override fun onClick(position: Int) {

        when (position) {
            0 -> {
                startActivity(Intent(requireActivity(), FindDonorActivity::class.java))
            }
            1 -> {
                startActivity(Intent(requireActivity(), DonationRequestsActivity::class.java))
            }
            2 -> {
                startActivity(Intent(requireActivity(), BloodBanksActivity::class.java))
            }
            3 -> {
                startActivity(Intent(requireActivity(), AssistantActivity::class.java))
            }
            4 -> {
                startActivity(Intent(requireActivity(), CampaignActivity::class.java))
            }
            5 -> {
                startActivity(Intent(requireActivity(), RequestBlood::class.java))
            }
        }
    }

    private fun sendNotification(token: String, name: String) {
        Toast.makeText(requireContext(), "sent Successfully", Toast.LENGTH_SHORT).show()
        val notificationSender = FcmNotificationSender(
            token,
            "Blood Donation Request Accepted",
            "$name Accepted Donation Request",
            requireContext(),
            requireActivity()
        )
        notificationSender.SendNotifications()
    }

    override fun onClick(uid: String, key: String) {
        val intent = Intent(requireActivity(), DonationDetailActivity::class.java)
        intent.putExtra("uid", uid)
        intent.putExtra("key", key)
        intent.putExtra("userSpecific", false)
        startActivity(intent)
//        var token = "empty"
//        var name = "empty"
//        val ref = FirebaseDatabase.getInstance().reference
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