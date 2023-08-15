package com.example.teaming

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class PjOutAdapter(private val dataList: ArrayList<PjOutData>) :
    RecyclerView.Adapter<PjOutAdapter.PjOutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PjOutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pj_out_item, parent, false)
        return PjOutViewHolder(view)
    }

    override fun onBindViewHolder(holder: PjOutViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class PjOutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.date)
        private val innerRecyclerView: RecyclerView = itemView.findViewById(R.id.in_recycler)

        fun bind(item: PjOutData) {
            dateTextView.text = item.date

            val innerAdapter = PjInAdapter(item.innerList)
            innerRecyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.VERTICAL, false)
                adapter = innerAdapter
            }

            //체크용 로그출력
            Log.d("PjOutData", "Date: ${item.date}")
            for (innerData in item.innerList) {
                Log.d("PjOutData", "Inner Data: File Name: ${innerData.file_name}, Comment Num: ${innerData.comment_num}")
            }
        }
    }
}