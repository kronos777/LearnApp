package com.rxlearn.retrofit.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.rxlearn.retrofit.R
import com.rxlearn.retrofit.data.product.Product
import com.rxlearn.retrofit.databinding.ListProductDummpyBinding

class ProductAdapter: ListAdapter<Product, ProductAdapter.Holder>(Comparator()) {
    class Holder(view: View): RecyclerView.ViewHolder(view) {
        private val binding = ListProductDummpyBinding.bind(view)

        fun bind(product: Product) = with(binding) {
            title.text = product.title
            description.text = product.description
        }
    }

    class Comparator : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_product_dummpy,
            parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position))
    }


}