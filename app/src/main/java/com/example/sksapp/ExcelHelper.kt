package com.example.sksapp

import android.content.Context
import com.example.sksapp.model.Report
import excelkt.Sheet
import excelkt.workbook
import excelkt.write
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.poi.ss.usermodel.IndexedColors
import org.apache.poi.xssf.usermodel.XSSFCellStyle
import java.io.*

class ExcelHelper(private val ctx: Context) {

    fun createExcel(report: Report, onSuccess: () -> Unit) {
        val dir = File(ctx.getExternalFilesDir(null), "excel")
        if (!dir.exists()) dir.mkdirs()

        val file = File(dir, "/laporan_${getDateForFile()}.xlsx")
        file.createNewFile()
        file.canWrite()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                workbook {
                    sheet("Sheet1") {
                        customersHeader()

                        var totalHarga = 0
                        for (i in report.list!!) {
                            row {
                                cell(i.name)
                                cell(i.price)
                                cell(i.count)
                                cell(i.price * i.count)
                            }
                            totalHarga += i.price * i.count
                        }

                        row {
                            cell("")
                            cell("")
                            cell("")
                            cell(totalHarga)
                        }
                    }
                }.write(file.absolutePath)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        onSuccess()
    }

    private fun Sheet.customersHeader() {
        val headers = arrayOf("Nama", "Harga", "Jumlah Terjual", "Total")

        val headingStyle = createCellStyle {
            setFont(createFont {
                fontName = "IMPACT"
                color = IndexedColors.WHITE.index
            })

            fillPattern = XSSFCellStyle.SOLID_FOREGROUND
            fillForegroundColor = IndexedColors.SEA_GREEN.index
        }

        row(headingStyle) {
            headers.forEach { cell(it) }
        }
    }

//    private fun storeExcelInStorage(workbook: Workbook, fileName: String, onSuccess: () -> Unit) {
//        var isSuccess: Boolean
//        val file = File(ctx.filesDir, fileName)
//        var fileOutputStream: FileOutputStream? = null
//        try {
//            fileOutputStream = FileOutputStream(file)
//            workbook.save(fileOutputStream)
//            Log.e(TAG, "Writing file$file")
//            isSuccess = true
//        } catch (e: IOException) {
//            Log.e(TAG, "Error writing Exception: ", e)
//            isSuccess = false
//        } catch (e: Exception) {
//            Log.e(TAG, "Failed to save file due to Exception: ", e)
//            isSuccess = false
//        } finally {
//            try {
//                fileOutputStream?.close()
//            } catch (ex: Exception) {
//                ex.printStackTrace()
//            }
//        }
//        if (isSuccess) { onSuccess() }
//    }
}