package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PjInAdapter(
    private val dataList: ArrayList<FileDetails>,
    private val itemClickListener: OnPjInItemClickListener,
    private val itemDelListener: OnPjInItemDelListener
) :
    RecyclerView.Adapter<PjInAdapter.PjInViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PjInViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pj_in_item, parent, false)
        return PjInViewHolder(view)
    }

    override fun onBindViewHolder(holder: PjInViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class PjInViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.file_name)
        private val commentNumTextView: TextView = itemView.findViewById(R.id.comment_num)
        private val oneDelBtn : ImageButton = itemView.findViewById(R.id.one_del_btn)


        fun bind(item: FileDetails) {
            fileNameTextView.text = item.file_name
            commentNumTextView.text = item.comment.toString()

            if(item.del_btn_mark == false){
                oneDelBtn.setImageResource(R.drawable.one_del_btn)
            }else{
                oneDelBtn.setImageResource(R.drawable.one_del_btn_blue)
            }

            itemView.setOnClickListener {
                itemClickListener.onPjInItemClick(item)
            }

            oneDelBtn.setOnClickListener{
                itemDelListener.onPjInItemDel(item)
            }

        }
    }

    interface OnPjInItemClickListener {
        fun onPjInItemClick(fileDetails: FileDetails)
    }

    interface OnPjInItemDelListener {
        fun onPjInItemDel(fileDetails: FileDetails)
    }
}
