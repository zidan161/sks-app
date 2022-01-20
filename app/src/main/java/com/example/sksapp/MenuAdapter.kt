package com.example.sksapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.sksapp.databinding.ItemMenuBinding
import com.example.sksapp.model.Menu
import com.example.sksapp.model.Order
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class MenuAdapter(private val add: (Menu) -> Unit, private val remove: (Menu) -> Unit, private val isOrder: Boolean) : RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    private var data = mutableListOf<Menu>()

    fun setData(data: List<Menu>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(menu: Menu) {
        this.data.add(menu)
        notifyItemInserted(data.size-1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = ItemMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bindItem(data[position])
    }

    override fun getItemCount(): Int = data.size

    inner class MenuViewHolder(private val binding: ItemMenuBinding): ViewHolder(binding.root) {
        
        private var count = 0

        fun bindItem(menu: Menu) {
            binding.tvName.text = menu.name
            Glide.with(binding.root)
                .load(menu.image)
                .fallback(R.drawable.ic_image_placeholder)
                .into(binding.imageView)
            binding.tvPrice.text = menu.price.toK()

            if (isOrder) {
                binding.btnPesan.visibility = View.VISIBLE
                binding.btnPesan.isClickable = true
                binding.layoutCount.visibility = View.GONE
                binding.tvCount.text = menu.count.toString()
            }

            binding.btnPesan.setOnClickListener {
                it.visibility = View.INVISIBLE
                it.isClickable = false
                binding.layoutCount.visibility = View.VISIBLE

                addMenu(menu)

                binding.btnIncrease.setOnClickListener {
                    addMenu(menu)
                }
                binding.btnDecrease.setOnClickListener {
                    removeMenu(menu)
                    checkIsZero(count)
                }
            }
        }

        private fun Int.toK() = "${this / 1000} K"

        private fun addMenu(menu: Menu) {
            add(menu)
            count++
            binding.tvCount.text = count.toString()
        }

        private fun removeMenu(menu: Menu) {
            remove(menu)
            count--
            binding.tvCount.text = count.toString()
        }

        private fun checkIsZero(count: Int) {
            if (count == 0) {
                binding.btnPesan.visibility = View.VISIBLE
                binding.btnPesan.isClickable = true
                binding.layoutCount.visibility = View.GONE
            }
        }
    }
}