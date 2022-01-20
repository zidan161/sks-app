package com.example.ownerapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ownerapp.databinding.ActivityMainBinding
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var db: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = Firebase.database

        val query = db.getReference("order")

        val options = FirebaseRecyclerOptions.Builder<ListOrder>()
            .setQuery(query, ListOrder::class.java)
            .build()

        orderAdapter = OrderAdapter(options) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra("data", it)
            startActivity(intent)
        }

        binding.rvMain.apply {
            adapter = orderAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            setHasFixedSize(true)
        }
    }

    override fun onStart() {
        super.onStart()
        orderAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        orderAdapter.stopListening()
    }
}