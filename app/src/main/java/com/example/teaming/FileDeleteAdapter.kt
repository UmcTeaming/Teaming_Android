package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileDeleteAdapter(
    private val filesToDelete: List<FileDetails>
) : RecyclerView.Adapter<FileDeleteAdapter.FileDeleteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileDeleteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pj_del_item, parent, false)
        return FileDeleteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FileDeleteViewHolder, position: Int) {
        val file = filesToDelete[position]
        holder.bind(file)
    }

    override fun getItemCount(): Int {
        return filesToDelete.size
    }

    inner class FileDeleteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.file_name)
        private val commentNumber: TextView = itemView.findViewById(R.id.comment_num)
        private val fileType : TextView = itemView.findViewById(R.id.file_type_name)

        fun bind(file: FileDetails) {
            name.text = file.file_name
            commentNumber.text = file.comment.toString()
            fileType.text = file.file_type
        }
    }

}
