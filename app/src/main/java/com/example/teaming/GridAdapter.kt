package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GridAdapter(val grid_itemList: ArrayList<GridListItem>): RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid,parent,false)
        return GridAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridAdapter.ViewHolder, position: Int) {
        holder.gridTitle.text = grid_itemList[position].grid_title
        holder.gridDate.text = grid_itemList[position].grid_date
    }

    override fun getItemCount(): Int {
        //return grid_itemList.size
        return 6
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val gridImg: ImageView = itemView.findViewById(R.id.grid_img)
        val gridTitle: TextView = itemView.findViewById(R.id.grid_title)
        val gridDate: TextView = itemView.findViewById(R.id.grid_date)
    }
}