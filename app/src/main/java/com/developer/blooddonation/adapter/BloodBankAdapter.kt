package com.developer.blooddonation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.developer.blooddonation.R
import com.developer.blooddonation.databinding.BloodBankSingleRowBinding
import com.developer.blooddonation.model.BloodBankModel

class BloodBankAdapter(private val list: List<BloodBankModel>, private val context: Context) :
    RecyclerView.Adapter<BloodBankAdapter.BloodBankViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BloodBankViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.blood_bank_single_row, parent, false)
        return BloodBankViewHolder(view)

    }

    override fun onBindViewHolder(holder: BloodBankViewHolder, position: Int) {
        Glide.with(context).load(list[position].imgUrl).into(holder.binding.imgBloodBank)
        holder.binding.txtBbName.text = list[position].bbName
        holder.binding.txtBbAddress.text = list[position].bbAddress
        holder.binding.txtBbLocation.text = list[position].cityName

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class BloodBankViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = BloodBankSingleRowBinding.bind(itemView)

    }

}