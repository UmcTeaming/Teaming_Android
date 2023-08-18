package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class FiInAdapter(
    private val dataList: ArrayList<FinalDetails>,
    private val itemClickListener: OnFiInItemClickListener
    ) :
    RecyclerView.Adapter<FiInAdapter.FiInViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiInViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fi_in_item, parent, false)
        return FiInViewHolder(view)
    }

    override fun onBindViewHolder(holder: FiInViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class FiInViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fileNameTextView: TextView = itemView.findViewById(R.id.file_name_fi)
        private val commentNumTextView: TextView = itemView.findViewById(R.id.comment_num_fi)

        fun bind(item: FinalDetails) {
            fileNameTextView.text = item.file_name
            commentNumTextView.text = item.comment.toString()

            itemView.setOnClickListener {
                itemClickListener.onFiInItemClick(item)
            }
        }
    }

    interface OnFiInItemClickListener {
        fun onFiInItemClick(finalDetails: FinalDetails)
    }
}
