package com.example.teaming

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FiOutAdapter(private val dataList: ArrayList<FinalFileData>) :
    RecyclerView.Adapter<FiOutAdapter.FiOutViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FiOutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fi_out_item, parent, false)
        return FiOutViewHolder(view)
    }

    override fun onBindViewHolder(holder: FiOutViewHolder, position: Int) {
        val item = dataList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class FiOutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val dateTextView: TextView = itemView.findViewById(R.id.date_fi)
        private val innerRecyclerView: RecyclerView = itemView.findViewById(R.id.in_recycler_fi)

        fun bind(item: FinalFileData) {
            dateTextView.text = item.createdAt.substring(0,10)

            val innerAdapter = FiInAdapter(ArrayList(item.filesDetails))

            innerRecyclerView.apply {
                layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.VERTICAL, false)
                adapter = innerAdapter
            }

            //체크용 로그출력
            Log.d("PjOutData", "Date: ${item.createdAt}")
            for (innerData in item.filesDetails) {
                Log.d("PjOutData", "Inner Data: File Name: ${innerData.file_name}, Comment Num: ${innerData.comment}")
            }
        }
    }
}