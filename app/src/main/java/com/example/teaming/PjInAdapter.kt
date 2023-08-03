package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PjInAdapter(private val dataList: ArrayList<PjInData>) :
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

        fun bind(item: PjInData) {
            fileNameTextView.text = item.file_name
            commentNumTextView.text = item.comment_num.toString()
        }
    }
}
