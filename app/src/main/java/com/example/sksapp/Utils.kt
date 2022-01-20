package com.example.sksapp

import android.annotation.SuppressLint
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

fun Int.setDecimal(): String {
    val format = DecimalFormat("##,###")
    return format.format(this)
}

@SuppressLint("SimpleDateFormat")
fun getDate(): String {
    return SimpleDateFormat("dd-MM-yyyy").format(Date())
}

@SuppressLint("SimpleDateFormat")
fun getDateForFile(): String {
    return SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
}

@SuppressLint("SimpleDateFormat")
fun getDateForReport(): String {
    val date = SimpleDateFormat("dd MMM yyyy").format(Date())
    return "Laporan tanggal: $date"
}

fun Int.toK() = "${this / 1000} K"