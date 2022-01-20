package com.example.ownerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ownerapp.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = intent.getParcelableExtra<ListOrder>("data")

        if (data != null) {
            binding.tvTable.text = "Meja No. ${data.table}"
            binding.tvDate.text = data.date

            binding.rvMakanan.apply {
                adapter = ItemAdapter(data.makanan)
                layoutManager = LinearLayoutManager(this@DetailActivity)
                setHasFixedSize(true)
            }

            binding.rvMinuman.apply {
                adapter = ItemAdapter(data.minuman)
                layoutManager = LinearLayoutManager(this@DetailActivity)
                setHasFixedSize(true)
            }
        }
    }
}