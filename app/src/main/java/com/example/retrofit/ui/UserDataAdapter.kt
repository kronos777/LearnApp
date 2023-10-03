package com.example.retrofit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofit.R
import com.example.retrofit.data.UserData

class UserDataAdapter(private val userData: List<UserData>) :
    RecyclerView.Adapter<UserDataAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = userData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.apply {
            id.text = userData[position].id.toString()
            name.text = userData[position].name
            userName.text = userData[position].username
            email.text = userData[position].email
            phone.text = userData[position].phone
            website.text = userData[position].website
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val id: TextView = itemView.findViewById(R.id.id_view)
        val name: TextView = itemView.findViewById(R.id.name_view)
        val userName: TextView = itemView.findViewById(R.id.user_name_view)
        val email: TextView = itemView.findViewById(R.id.email_view)
        val phone: TextView = itemView.findViewById(R.id.phone_view)
        val website: TextView = itemView.findViewById(R.id.website_view)
    }
}