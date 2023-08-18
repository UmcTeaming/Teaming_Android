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
        holder.horImg.setImageResource(R.drawable.file_background)
        holder.horTitle.text = hor_itemList[position].hor_title
        holder.horDate.text = hor_itemList[position].hor_date

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return hor_itemList.size
        //return 3
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val horImg: ImageView = itemView.findViewById(R.id.hor_img)
        val horTitle: TextView = itemView.findViewById(R.id.hor_title)
        val horDate: TextView = itemView.findViewById(R.id.hor_date)
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