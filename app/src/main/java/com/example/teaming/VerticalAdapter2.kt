package com.example.teaming

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VerticalAdapter2(val ver_itemList: ArrayList<VerListItem>): RecyclerView.Adapter<VerticalAdapter2.ViewHolder>() {

    private var itemClickListener: OnItemClickListener?= null
    private var itemLongClickListener: OnItemLongClickListener?=null

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    interface OnItemLongClickListener {
        fun onLongClick(v: View, position: Int)
    }

    fun setItemLongClickListener(listener: OnItemLongClickListener) {
        this.itemLongClickListener = listener
    }

    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalAdapter2.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ver2,parent,false)
        return VerticalAdapter2.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ver_itemList.size
    }

    override fun onBindViewHolder(holder: VerticalAdapter2.ViewHolder, position: Int) {
        holder.verTitle.text = ver_itemList[position].ver_title
        holder.verDate.text = ver_itemList[position].ver_date

        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }

        val status = ver_itemList[position].ver_status
        Log.e("status","${status}")
        if (status == "ING") {
            holder.verState.setImageResource(R.drawable.circle)
        } else {
            holder.verState.setImageResource(R.drawable.circle_end)
        }

        holder.itemView.setOnLongClickListener {
            itemLongClickListener?.onLongClick(it, position)
            true // 이벤트 소비를 표시하기 위해 true 반환
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val verState: ImageView = itemView.findViewById(R.id.ver_state2)
        val verTitle: TextView = itemView.findViewById(R.id.ver_title2)
        val verDate: TextView = itemView.findViewById(R.id.ver_date2)
    }
}