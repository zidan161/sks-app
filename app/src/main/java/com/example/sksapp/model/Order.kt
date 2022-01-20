package com.example.sksapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    var makanan: List<Menu> = mutableListOf(),
    var minuman: List<Menu> = mutableListOf(),
    var date: String? = null,
    var table: String = "",
    var total: Int = 0
): Parcelable
