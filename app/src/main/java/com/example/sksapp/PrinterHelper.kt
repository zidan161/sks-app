package com.example.sksapp

import android.util.DisplayMetrics
import android.content.Context
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import com.example.sksapp.model.Order

class PrinterHelper(private val ctx: Context) {

    fun printText(data: Order, total: String) {

        var textMenus = ""

        for (i in data.makanan) {
            textMenus += """[L]<b>${i.name}</b>[R]${i.price / 100} K" +
                         [L]  ${i.count} x
                         [L]
                         """.trimIndent()
        }

        val printer = EscPosPrinter(BluetoothPrintersConnections.selectFirstPaired(), 203, 48f, 32)
        printer
            .printFormattedText(
                """
        [C]<img>${
                    PrinterTextParserImg.bitmapToHexadecimalString(
                        printer,
                        ctx.applicationContext.resources
                            .getDrawableForDensity(R.drawable.ssks_logo, DisplayMetrics.DENSITY_MEDIUM, ctx.applicationContext.resources.newTheme())
                    )
                }</img>
        [L]
        [C]<u><font size='big'>Nota Pembelian</font></u>
        [L]
        [L]No. Meja: ${data.table}
        [C]================================
        [L]
        $textMenus
        [C]--------------------------------
        [R]TOTAL HARGA :[R]$total
        [L]
        [C]================================
        [L]
        [C]Terima Kasih :)
        """.trimIndent())
    }
}