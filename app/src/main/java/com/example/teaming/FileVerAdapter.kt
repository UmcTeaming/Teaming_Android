package com.example.teaming

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class FileVerAdapter(val file_ver_itemList: ArrayList<GridListItem>): RecyclerView.Adapter<FileVerAdapter.ViewHolder>() {

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileVerAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_file_ver,parent,false)
        return FileVerAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileVerAdapter.ViewHolder, position: Int) {
        holder.gridTitle.text = file_ver_itemList[position].grid_title
        holder.gridDate.text = file_ver_itemList[position].grid_date

        // 리사이클러뷰 클릭 이벤트
        holder.itemView.setOnClickListener {
            itemClickListener?.onClick(it, position)
        }

        Glide.with(holder.itemView.context)
            .load(file_ver_itemList[position].gridImg) // 이미지 URL 또는 리소스 ID
            .error(R.drawable.file_background) // 에러 발생 시 표시할 이미지 리소스
            .into(holder.gridImg) // 이미지가 표시될 ImageView

        val status = file_ver_itemList[position].grid_status
        Log.e("status","${status}")
        if (status == "ING") {
            holder.stateCol.setImageResource(R.drawable.circle)
        } else {
            holder.stateCol.setImageResource(R.drawable.circle_end)
        }

        holder.itemView.setOnLongClickListener {
            itemLongClickListener?.onLongClick(it, position)
            true // 이벤트 소비를 표시하기 위해 true 반환
        }
    }

    override fun getItemCount(): Int {
        return file_ver_itemList.size
    }

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val gridImg: ImageView = itemView.findViewById(R.id.grid_img)
        val gridTitle: TextView = itemView.findViewById(R.id.grid_title)
        val gridDate: TextView = itemView.findViewById(R.id.grid_date)
        val stateCol: ImageView = itemView.findViewById(R.id.file_ver_state)
    }

    // (2) 리스너 인터페이스
    /*interface OnItemClickListener {
        fun onClick(v: View, position: Int)
    }
    // (3) 외부에서 클릭 시 이벤트 설정
    fun setItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }
    // (4) setItemClickListener로 설정한 함수 실행
    //private lateinit var itemClickListener : OnItemClickListener*/
}