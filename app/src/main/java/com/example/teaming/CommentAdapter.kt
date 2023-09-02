package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class CommentAdapter(private val comments: List<CommentData>, private val onItemLongClickListener: (CommentData) -> Unit) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.comment_item, parent, false)
        return CommentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

        val comment = comments[position]
        holder.bind(comment)

        holder.itemView.setOnLongClickListener {
            // 여기서 commentId를 가져와서 로그로 출력
            val commentId = comment.commentId



            true // true를 반환하여 이벤트 소비 처리
        }

        holder.apply {
            Glide.with(itemView.context)
                .load(comment.profile_image)
                .error(R.drawable.profile_default)
                .into(imgView)

            itemView.setOnLongClickListener {
                onItemLongClickListener(comment)
                true // true를 반환하여 이벤트 소비 처리
            }

        }
    }

    override fun getItemCount(): Int {
        return comments.size
    }

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.writer_name)
        private val dateTextView: TextView = itemView.findViewById(R.id.comment_date)
        private val contextView: TextView = itemView.findViewById(R.id.comment_context)
        val imgView: ImageView = itemView.findViewById(R.id.comment_img)

        fun bind(item: CommentData) {
            nameTextView.text = item.writer
            dateTextView.text = "${item.create_at.substring(0,10)} ${item.create_at.substring(11,16)}"
            contextView.text = item.content


        }
    }
}
