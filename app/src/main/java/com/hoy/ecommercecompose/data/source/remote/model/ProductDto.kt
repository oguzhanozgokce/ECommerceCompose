package com.hoy.ecommercecompose.data.source.remote.model

data class ProductDto(
    val category: String?,
    val count: Int?,
    val description: String?,
    val id: Int?,
    val imageOne: String?,
    val imageThree: String?,
    val imageTwo: String?,
    val price: Double?,
    val rate: Double?,
    val salePrice: Double?,
    val saleState: Boolean?,
    val title: String?
)
