package com.developer.blooddonation.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.developer.blooddonation.activities.AuthenticationActivity
import com.developer.blooddonation.activities.RequestedActivity
import com.developer.blooddonation.databinding.FragmentProfileBinding
import com.developer.blooddonation.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class ProfileFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var ref: DatabaseReference
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid.toString()
        ref = FirebaseDatabase.getInstance().reference
        getUserData(uid)
        binding.donateSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
            updateInDatabase(isChecked, uid)

        }
        binding.requestedBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), RequestedActivity::class.java))

        }

        binding.customerCareBtn.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "7207826813", null))
            startActivity(intent)
        }
        binding.signout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(requireActivity(), AuthenticationActivity::class.java)

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //makesure user cant go back

            startActivity(intent)
        }
        return binding.root
    }

    private fun updateInDatabase(checked: Boolean, uid: String) {

        binding.profileProgressBar.visibility = View.VISIBLE
        binding.progressLayout.visibility = View.VISIBLE
        ref.child("users").child(uid).child("readyToDonate").setValue(checked)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    binding.profileProgressBar.visibility = View.INVISIBLE
                    binding.progressLayout.visibility = View.INVISIBLE
                } else {
                    binding.profileProgressBar.visibility = View.INVISIBLE
                    binding.progressLayout.visibility = View.INVISIBLE
                    Toast.makeText(requireContext(), "Unable to change status", Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }

    private fun getUserData(uid: String) {
        binding.profileProgressBar.visibility = View.VISIBLE
        binding.progressLayout.visibility = View.VISIBLE


        ref.child("users").child(uid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val request = snapshot.getValue(User::class.java)
                    binding.txtName.text = request?.username.toString()
                    binding.txtCity.text = request?.city.toString()
                    binding.txtBloodGroup.text = request?.bloodGroup.toString()
                    binding.txtRequested.text = request?.requested.toString()
                    binding.txtDonated.text = request?.donated.toString()
                    binding.donateSwitch.isChecked = request?.readyToDonate!!
                    binding.profileProgressBar.visibility = View.INVISIBLE
                    binding.progressLayout.visibility = View.INVISIBLE


                }
            }

            override fun onCancelled(error: DatabaseError) {
                binding.profileProgressBar.visibility = View.INVISIBLE
                binding.progressLayout.visibility = View.INVISIBLE
            }

        })

    }


}