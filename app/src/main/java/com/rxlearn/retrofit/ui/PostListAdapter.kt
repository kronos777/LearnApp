package com.rxlearn.retrofit.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rxlearn.retrofit.R
import com.rxlearn.retrofit.data.post.Post

class PostListAdapter(private val postData: List<Post>) :
    RecyclerView.Adapter<PostListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.post_item, parent, false)
        return MyViewHolder(view)
    }
    override fun getItemCount(): Int = postData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.apply {
            title.text = postData[position].title
            description.text = postData[position].body
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
    }
}