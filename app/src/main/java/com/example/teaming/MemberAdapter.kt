package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(val itemList: ArrayList<MemberData>) :
    RecyclerView.Adapter<MemberAdapter.MemberViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.member_item, parent, false)
        return MemberViewHolder(view)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.m_image.setImageResource(itemList[position].img)
    }

    override fun getItemCount(): Int {
        return itemList.count()
    }


    inner class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val m_image = itemView.findViewById<ImageView>(R.id.imageView)
    }
}