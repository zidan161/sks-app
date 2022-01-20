package com.example.sksapp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Report(
    var date: String? = null,
    var list: List<Menu>? = null
): Parcelable
