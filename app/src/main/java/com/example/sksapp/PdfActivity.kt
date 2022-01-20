package com.example.sksapp

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.core.view.marginBottom
import com.example.sksapp.model.Report
import com.tejpratapsingh.pdfcreator.activity.PDFCreatorActivity
import com.tejpratapsingh.pdfcreator.utils.PDFUtil
import com.tejpratapsingh.pdfcreator.views.PDFBody
import com.tejpratapsingh.pdfcreator.views.PDFFooterView
import com.tejpratapsingh.pdfcreator.views.PDFHeaderView
import com.tejpratapsingh.pdfcreator.views.basic.PDFImageView
import com.tejpratapsingh.pdfcreator.views.basic.PDFTextView
import com.tejpratapsingh.pdfcreator.views.basic.PDFVerticalView
import java.io.File
import com.tejpratapsingh.pdfcreator.views.PDFTableView
import com.tejpratapsingh.pdfcreator.views.PDFTableView.PDFTableRowView
import java.lang.Exception

class PdfActivity : PDFCreatorActivity() {

    private lateinit var data: Report

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        data = intent.getParcelableExtra("data")!!

        createPDF("data", object : PDFUtil.PDFUtilListener {
            override fun pdfGenerationSuccess(savedPDFFile: File?) {
                Toast.makeText(this@PdfActivity, "PDF Created", Toast.LENGTH_SHORT).show()
            }

            override fun pdfGenerationFailure(exception: Exception?) {
                TODO("Not yet implemented")
            }
        })
    }

    override fun getHeaderView(forPage: Int): PDFHeaderView {
        val headerView = PDFHeaderView(applicationContext)

        val verticalView = PDFVerticalView(applicationContext)
        val image = PDFImageView(applicationContext).setImageResource(R.drawable.ssks_logo)
        val header = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.H1).setText("Laporan Harian")

        verticalView.view.gravity = Gravity.CENTER_HORIZONTAL

        image.view.adjustViewBounds = true
        image.view.maxHeight = 180
        image.view.setPadding(10, 0, 10, 30)

        verticalView.addView(image)
        verticalView.addView(header)

        headerView.addView(verticalView)

        return headerView
    }

    override fun getBodyViews(): PDFBody {
        val bodyView = PDFBody()

        val headerRow = PDFTableRowView(applicationContext)

        val headers = arrayOf("Nama", "Harga", "Jumlah Terjual", "Total")
        for (i in headers) {
            val pdfTextView = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P)
            pdfTextView.setText(i)
            headerRow.addToRow(pdfTextView)
        }

        val firstRow = getFirstRow()

        val tableView = PDFTableView(applicationContext, headerRow, firstRow)

        for (i in data.list!!.drop(1)) {
            val dataRow = PDFTableRowView(applicationContext)
            val name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(i.name)
            val price = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(i.price.setDecimal())
            val sold = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(i.count.toString())
            val total = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText((i.price*i.count).setDecimal())

            dataRow.addToRow(name)
            dataRow.addToRow(price)
            dataRow.addToRow(sold)
            dataRow.addToRow(total)

            tableView.addRow(dataRow)
        }
        bodyView.addView(tableView)

        return bodyView
    }

    override fun getFooterView(forPage: Int): PDFFooterView {
        val footer = PDFFooterView(this)

        val name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText("SKS 2022")
        name.view.gravity = Gravity.HORIZONTAL_GRAVITY_MASK

        footer.addView(name)
        return footer
    }

    override fun onNextClicked(savedPDFFile: File?) {
        finish()
    }

    private fun getFirstRow(): PDFTableRowView {
        val firstRow = PDFTableRowView(applicationContext)
        val firstData = data.list!![0]
        val name = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(firstData.name)
        val price = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(firstData.price.setDecimal())
        val sold = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText(firstData.count.toString())
        val total = PDFTextView(applicationContext, PDFTextView.PDF_TEXT_SIZE.P).setText((firstData.price*firstData.count).setDecimal())

        firstRow.addToRow(name)
        firstRow.addToRow(price)
        firstRow.addToRow(sold)
        firstRow.addToRow(total)

        return firstRow
    }
}