package com.rxlearn.retrofit.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.rxlearn.retrofit.R
import com.rxlearn.retrofit.data.news.PhotoData

class PhotoDataAdapter(private val photoData: List<PhotoData>) :
    RecyclerView.Adapter<PhotoDataAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_layout_photo, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int = photoData.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.apply {
            title.text = photoData[position].title
            val mImageAddress = photoData[position].thumbnailUrl
            Glide
                .with(holder.itemView.getContext())
                .load(mImageAddress)
                .into(src)
            //Log.d("imageSrc", photoData[position].thumbnailUrl)
           // src.setImageURI(photoData[position].thumbnailUrl.toUri())
           // src.ima =
            /*userName.text = photoData[position].username
            email.text = photoData[position].email
            phone.text = photoData[position].phone
            website.text = photoData[position].website*/
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title_view)
        val src: ImageView = itemView.findViewById(R.id.image_view)
       /* val userName: TextView = itemView.findViewById(R.id.user_name_view)
        val email: TextView = itemView.findViewById(R.id.email_view)
        val phone: TextView = itemView.findViewById(R.id.phone_view)
        val website: TextView = itemView.findViewById(R.id.website_view)*/
    }
}