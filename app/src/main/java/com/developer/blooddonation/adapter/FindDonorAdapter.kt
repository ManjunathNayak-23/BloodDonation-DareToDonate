package com.developer.blooddonation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.developer.blooddonation.R
import com.developer.blooddonation.databinding.FindDonorSingleRowBinding
import com.developer.blooddonation.model.User

class FindDonorAdapter(
    private val list: List<User>,
    private val context: Context,
    val listener: OnClickListener
) :
    RecyclerView.Adapter<FindDonorAdapter.FindDonorViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FindDonorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.find_donor_single_row, parent, false)
        return FindDonorViewHolder(view)
    }

    override fun onBindViewHolder(holder: FindDonorViewHolder, position: Int) {
        holder.binding.personName.text = list[position].username
        holder.binding.personAddress.text = list[position].city
        holder.binding.bloodGroup.text = list[position].bloodGroup
//        Glide.with(context).load(list[position].image).into(holder.binding.profileImg)
//        Glide.with(context).load(list[position].bloodGroup).into(holder.binding.personBloodGroup)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class FindDonorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val binding = FindDonorSingleRowBinding.bind(itemView)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onClick(position)

            }
        }
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

}
