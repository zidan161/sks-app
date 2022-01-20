package com.example.sksapp

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TableRow
import android.widget.TextView
import com.example.sksapp.databinding.ActivityReportBinding
import android.widget.TableLayout
import android.widget.Toast
import com.example.sksapp.model.Menu
import com.example.sksapp.model.Report
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class ReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportBinding
    private lateinit var db: FirebaseDatabase

    private var report: Report? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            // Request permission from the user
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
        }

        binding.tvDate.text = getDateForReport()

        db = Firebase.database

        db.getReference("report").child(getDate()).get().addOnSuccessListener {

            val listReport = mutableListOf<Menu>()

            it.children.forEachIndexed { i, data ->

                val menu = data.getValue(Menu::class.java)
                listReport.add(menu!!)

                val row = TableRow(this)

                val layoutParam = TableLayout.LayoutParams()

                layoutParam.topMargin = if (i == 0) 20 else 10

                row.layoutParams = layoutParam

                val name = TextView(this)
                val price = TextView(this)
                val sold = TextView(this)
                val total = TextView(this)

                name.text = menu.name
                price.text = menu.price.setDecimal()
                sold.text = menu.count.toString()
                total.text = (menu.price * menu.count).setDecimal()

                name.textSize = 16f
                price.textSize = 16f
                sold.textSize = 16f
                total.textSize = 16f

                row.apply {
                    addView(name)
                    addView(price)
                    addView(sold)
                    addView(total)
                }

                binding.tableLayout.addView(row)
            }
            report = Report(getDate(), listReport)

            binding.pgBar.visibility = View.GONE
            binding.tableLayout.visibility = View.VISIBLE
            binding.btnExcel.visibility = View.VISIBLE
            binding.btnPdf.visibility = View.VISIBLE
        }

        binding.btnExcel.setOnClickListener {
            ExcelHelper(applicationContext).createExcel(report!!) {
                Toast.makeText(this, "Excel berhasil dibuat", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPdf.setOnClickListener {
            val intent = Intent(this, PdfActivity::class.java)
            intent.putExtra("data", report!!)
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 0) {

        }
    }
}