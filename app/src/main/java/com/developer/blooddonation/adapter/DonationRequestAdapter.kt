package com.developer.blooddonation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.developer.blooddonation.R
import com.developer.blooddonation.model.DonationRequests
import com.github.marlonlom.utilities.timeago.TimeAgo


class DonationRequestAdapter(
    private val list: List<DonationRequests>,
    val context: Context,
    val listener: OnClickListener
) : RecyclerView.Adapter<DonationRequestAdapter.DonationRequestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonationRequestViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.donor_request_single_row, parent, false)
        return DonationRequestViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonationRequestViewHolder, position: Int) {

        holder.nameTv.text = list[position].name
        holder.location.text = list[position].location
        holder.uploadedDate.text = TimeAgo.using(list[position].timeStamp?.toLong()!!)
        holder.bloodGroup.text = list[position].bloodRequest
//        if (list[position].accepted == true) {
//            holder.donateBtn.text = "Accepted"
//        }


    }


    override fun getItemCount(): Int {
        return list.size
    }

    inner class DonationRequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val nameTv = itemView.findViewById<TextView>(R.id.nameTxt)
        val location = itemView.findViewById<TextView>(R.id.locationTxt)
        val uploadedDate = itemView.findViewById<TextView>(R.id.uploadedDate)
        val bloodGroup = itemView.findViewById<TextView>(R.id.bloodGroup)
        val donateBtn = itemView.findViewById<Button>(R.id.donateBtn)

        init {
            donateBtn.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
//            if(donateBtn.text=="Accepted"){
//                Toast.makeText(context,"User will reach you out shortly",Toast.LENGTH_SHORT).show()
//            }else{
                listener.onClick(list[adapterPosition].uid.toString(),list[adapterPosition].key.toString())

//            }
        }


    }

    interface OnClickListener {
        fun onClick(uid: String,key:String)
    }
}