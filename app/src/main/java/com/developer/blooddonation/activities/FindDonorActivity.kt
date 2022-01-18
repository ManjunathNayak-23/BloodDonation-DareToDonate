package com.developer.blooddonation.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.developer.blooddonation.R
import com.developer.blooddonation.adapter.FindDonorAdapter
import com.developer.blooddonation.databinding.ActivityFindDonorBinding
import com.developer.blooddonation.model.User
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.*


class FindDonorActivity : AppCompatActivity(), FindDonorAdapter.OnClickListener {
    private lateinit var binding: ActivityFindDonorBinding
    private lateinit var list: ArrayList<User>
    private lateinit var ref: DatabaseReference
    private lateinit var adapter: FindDonorAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFindDonorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        list = ArrayList<User>()
        adapter = FindDonorAdapter(list, this, this)
        binding.donorRecyclerview.adapter = adapter
        binding.donorRecyclerview.setHasFixedSize(true)

        getUserData()


    }

    private fun getUserData() {
        list.clear()
        ref = FirebaseDatabase.getInstance().reference
        ref.child("users").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1: DataSnapshot in snapshot.children) {
                    val result = snapshot1.getValue(User::class.java)!!
                    list.add(result)
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    @SuppressLint("SetTextI18n")
    override fun onClick(position: Int) {
        val bottomSheetDialogue = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        val bottomSheetView = LayoutInflater.from(applicationContext)
            .inflate(R.layout.layout_bottom_sheet, findViewById(R.id.bottomSheetContainer))
        bottomSheetView.findViewById<TextView>(R.id.txt_bsname).text = list[position].username
        bottomSheetView.findViewById<TextView>(R.id.txt_bscity).text = list[position].city
        val donatedimes=list[position].donated.toString()
        bottomSheetView.findViewById<TextView>(R.id.txt_bdonated).text = "$donatedimes Times Donated"

        val bloodGroup= list[position].bloodGroup
        bottomSheetView.findViewById<TextView>(R.id.txt_bt).text ="Blood Type- $bloodGroup"
        bottomSheetView.findViewById<Button>(R.id.btn_callNow).setOnClickListener {
            val number=list[position].phoneNumber
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
            startActivity(intent)
        }
        bottomSheetView.findViewById<Button>(R.id.btn_Request).setOnClickListener {
            val intent=Intent(this,RequestBlood::class.java)
            intent.putExtra("uidOfRequested",list[position].uid)
            startActivity(intent)

        }
        bottomSheetDialogue.setContentView(bottomSheetView)
        bottomSheetDialogue.show()

    }


}