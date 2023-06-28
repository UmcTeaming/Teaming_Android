package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HorizontalAdapter(val hor_itemList: ArrayList<HorListItem>): RecyclerView.Adapter<HorizontalAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorizontalAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_hori,parent,false)
        return HorizontalAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorizontalAdapter.ViewHolder, position: Int) {
        //holder.horImg.adjustViewBounds = hor_itemList[position].hor_img
        holder.horTitle.text = hor_itemList[position].hor_title
        holder.horDate.text = hor_itemList[position].hor_date
    }

    override fun getItemCount(): Int {
        //return hor_itemList.size
        return 3
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val horImg: ImageView = itemView.findViewById(R.id.hor_img)
        val horTitle: TextView = itemView.findViewById(R.id.hor_title)
        val horDate: TextView = itemView.findViewById(R.id.hor_date)
    }
}