package com.developer.blooddonation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.developer.blooddonation.R
import com.developer.blooddonation.model.MainMenuModel

class MainGridAdapter(
    private val list: List<MainMenuModel>,
    private val context: Context,
    private val listener: onItemClickListener
) :
    RecyclerView.Adapter<MainGridAdapter.MainViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.main_grid_layout, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        Glide.with(context).load(list[position].img).skipMemoryCache(false).diskCacheStrategy(
            DiskCacheStrategy.AUTOMATIC
        ).into(holder.imgLogo)
        holder.menuName.text = list[position].itemName
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        val imgLogo = itemView.findViewById<ImageView>(R.id.menuLogo)
        val menuName = itemView.findViewById<TextView>(R.id.menuItem)

        init {

            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position=adapterPosition
            if(position!=RecyclerView.NO_POSITION){
                listener.onClick(adapterPosition)
            }

        }

    }

    interface onItemClickListener {
        fun onClick(position: Int)
    }

}