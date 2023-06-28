package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VerticalAdapter(val ver_itemList: ArrayList<VerListItem>): RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    // 아이템 레이아웃과 결헙
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ver,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        //return ver_itemList.size
        return 3
    }

    override fun onBindViewHolder(holder: VerticalAdapter.ViewHolder, position: Int) {
        holder.verTitle.text = ver_itemList[position].ver_title
        holder.verDate.text = ver_itemList[position].ver_date
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val verTitle: TextView = itemView.findViewById(R.id.ver_title)
        val verDate: TextView = itemView.findViewById(R.id.ver_date)
    }
}