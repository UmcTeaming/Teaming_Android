package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileVerAdapter(val file_ver_itemList: ArrayList<GridListItem>): RecyclerView.Adapter<FileVerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileVerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_ver,parent,false)
        return FileVerAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileVerAdapter.ViewHolder, position: Int) {
        holder.gridTitle.text = file_ver_itemList[position].grid_title
        holder.gridDate.text = file_ver_itemList[position].grid_date

        // 리사이클러뷰 클릭 이벤트
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }
    }

    override fun getItemCount(): Int {
        return file_ver_itemList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        //val gridImg: ImageView = itemView.findViewById(R.id.grid_img)
        val gridTitle: TextView = itemView.findViewById(R.id.grid_title)
        val gridDate: TextView = itemView.findViewById(R.id.grid_date)
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