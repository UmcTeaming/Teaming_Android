package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class GridAdapter(val grid_itemList: ArrayList<GridListItem>): RecyclerView.Adapter<GridAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GridAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid,parent,false)
        return GridAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: GridAdapter.ViewHolder, position: Int) {
        holder.gridTitle.text = grid_itemList[position].grid_title
        holder.gridDate.text = grid_itemList[position].grid_date

        // 리사이클러뷰 클릭 이벤트
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(it, position)
        }

        Glide.with(holder.itemView.context)
            .load(grid_itemList[position].gridImg) // 이미지 URL 또는 리소스 ID
            .error(R.drawable.file_background) // 에러 발생 시 표시할 이미지 리소스
            .into(holder.gridImg) // 이미지가 표시될 ImageView
    }

    override fun getItemCount(): Int {
        return grid_itemList.size
        //return 6
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val gridImg: ImageView = itemView.findViewById(R.id.grid_img)
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