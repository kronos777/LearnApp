package com.rxlearn.retrofit.data.product

data class Product(
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: String,
    val rating: Double,
    val stock: Int,
    val thumbnail: String,
    val title: String
)