package com.example.ownerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ownerapp.databinding.ItemOrderBinding
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class OrderAdapter(data: FirebaseRecyclerOptions<ListOrder>, private val detail: (ListOrder) -> Unit): FirebaseRecyclerAdapter<ListOrder, OrderAdapter.ViewHolder>(data) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, detail)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ListOrder) {
        holder.bindItem(model)
    }

    class ViewHolder(private val binding: ItemOrderBinding, private val detail: (ListOrder) -> Unit): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(order: ListOrder) {
            binding.tvTable.text = "No. Meja: ${order.table}"
            binding.tvDate.text = order.date

            val menu = if (order.makanan.isNotEmpty()) {
                order.makanan[0]
            } else {
                order.minuman[0]
            }

            val allListCount: Int = order.makanan.size + order.minuman.size - 1

            if (allListCount > 0) {
                binding.tvLainnya.text = "dan $allListCount lainnya..."
            }

            Glide.with(binding.root).load(menu.image).fallback(R.drawable.ic_picture_placeholder).into(binding.img1)
            binding.tvName1.text = menu.name
            binding.tvPrice1.text = "${menu.price / 1000} K"
            binding.btnDetail.setOnClickListener { detail(order) }
        }
    }
}