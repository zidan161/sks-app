package com.example.sksapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Menu(
    var name: String = "",
    var price: Int = 0,
    var image: String? = null,
    var count: Int = 1
): Parcelable
