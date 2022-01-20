package com.example.sksapp

import android.content.Context
import android.view.Gravity
import com.tejpratapsingh.pdfcreator.views.basic.PDFImageView
import com.tejpratapsingh.pdfcreator.views.basic.PDFTextView
import com.tejpratapsingh.pdfcreator.views.basic.PDFVerticalView

class PdfHelper {

    fun toPdf(ctx: Context) {
        val verticalView = PDFVerticalView(ctx)
        val image = PDFImageView(ctx).setImageResource(R.drawable.ssks_logo)
        val header = PDFTextView(ctx, PDFTextView.PDF_TEXT_SIZE.H1).setText("Laporan Harian")

        verticalView.view.gravity = Gravity.CENTER_HORIZONTAL

        image.view.adjustViewBounds = true

        verticalView.addView(image)
        verticalView.addView(header)
        val layout = verticalView.view


    }
}