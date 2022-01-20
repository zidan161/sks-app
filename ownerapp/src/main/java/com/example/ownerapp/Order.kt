package com.example.ownerapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListOrder(
    var makanan: List<Order> = mutableListOf(),
    var minuman: List<Order> = mutableListOf(),
    var date: String? = null,
    var table: String = ""
): Parcelable

@Parcelize
data class Order(
    var name: String = "",
    var price: Int = 0,
    var image: String? = null,
    var count: Int = 0
): Parcelable
