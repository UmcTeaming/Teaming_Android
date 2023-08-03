package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class VerticalAdapter(val ver_itemList: ArrayList<VerListItem>): RecyclerView.Adapter<VerticalAdapter.ViewHolder>() {

    // 아이템 레이아웃과 결합
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VerticalAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ver,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return ver_itemList.size
        //return 3
    }

    override fun onBindViewHolder(holder: VerticalAdapter.ViewHolder, position: Int) {
        holder.verTitle.text = ver_itemList[position].ver_title
        holder.verDate.text = ver_itemList[position].ver_date

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val verState: Int = itemView.findViewById(R.id.ver_state)
        val verTitle: TextView = itemView.findViewById(R.id.ver_title)
        val verDate: TextView = itemView.findViewById(R.id.ver_date)
    }

    // (2) 리스너 인터페이스
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