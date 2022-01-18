package com.developer.blooddonation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.blooddonation.R
import com.developer.blooddonation.databinding.MyRequestSingleRowBinding
import com.developer.blooddonation.model.MyRequestModel
import com.github.marlonlom.utilities.timeago.TimeAgo

class MyRequestsAdapter(private val list: List<MyRequestModel>) :
    RecyclerView.Adapter<MyRequestsAdapter.MyRequestsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyRequestsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.my_request_single_row, parent, false)
        return MyRequestsViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyRequestsViewHolder, position: Int) {
        holder.binding.txtUsername.text = list[position].name
        holder.binding.txtBg.text = list[position].bloodRequest
        holder.binding.txtTime.text = TimeAgo.using(list[position].timeStamp?.toLong()!!)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyRequestsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = MyRequestSingleRowBinding.bind(itemView)

    }
}