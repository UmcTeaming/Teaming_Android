package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VerticalAdapter2(val ver_itemList: ArrayList<VerListItem>): RecyclerView.Adapter<VerticalAdapter2.ViewHolder>() {
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
            itemClickListener.onClick(it, position)
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val verState: Int = itemView.findViewById(R.id.ver_state)
        val verTitle: TextView = itemView.findViewById(R.id.ver_title2)
        val verDate: TextView = itemView.findViewById(R.id.ver_date2)
    }

    interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    private lateinit var itemClickListener : OnItemClickListener
}