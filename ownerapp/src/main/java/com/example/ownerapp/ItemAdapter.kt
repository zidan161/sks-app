package com.example.ownerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ownerapp.databinding.ItemDetailBinding

class ItemAdapter(private val data: List<Order>) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class ViewHolder(private val binding: ItemDetailBinding): RecyclerView.ViewHolder(binding.root) {
        fun bindItem(order: Order) {
            binding.tvName.text = order.name
            Glide.with(binding.root)
                .load(order.image)
                .fallback(R.drawable.ic_picture_placeholder)
                .into(binding.imageView)
            binding.tvPrice.text = order.price.toK()
            binding.tvQty.text = "${order.count} x"
        }

        private fun Int.toK() = "${this / 1000} K"
    }
}