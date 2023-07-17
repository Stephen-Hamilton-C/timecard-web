package com.github.stephenhamiltonc.timecard_web.core

import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import kotlinx.browser.document
import com.github.stephenhamiltonc.timecard.Timecard
import kotlinx.datetime.LocalDate

class LogExporter(val timecard: Timecard) {
    fun exportCSV(range: ClosedRange<LocalDate>? = null) {
        val entries = if(range != null) {
            timecard.filterByDateRange(range)
        } else {
            timecard.entries
        }

        var csvData = "Start Time,End Time\n"
        for(entry in entries) {
            csvData += "${entry.start}"
            entry.end?.let {
                csvData += ",$it"
            }
            csvData += "\n"
        }

        downloadExportedFile(csvData)
    }

    private fun downloadExportedFile(fileData: String) {
        val blob = Blob(arrayOf(fileData), BlobPropertyBag("text/csv"))
        val anchorElement = document.createElement("a") as HTMLAnchorElement
        // TODO: put dates in file name
        anchorElement.download = "Timecard Log.csv"
        anchorElement.href = URL.createObjectURL(blob)
        anchorElement.click()
    }
}
