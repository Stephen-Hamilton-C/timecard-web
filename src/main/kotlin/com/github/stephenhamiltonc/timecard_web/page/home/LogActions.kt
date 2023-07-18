package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import io.kvision.core.Container
import io.kvision.core.TooltipOptions
import io.kvision.core.enableTooltip
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.utils.auto
import io.kvision.utils.px
import kotlinx.browser.document
import kotlinx.datetime.LocalDate
import org.w3c.dom.HTMLAnchorElement
import org.w3c.dom.url.URL
import org.w3c.files.Blob
import org.w3c.files.BlobPropertyBag

class LogActions(private val _timecard: Timecard) : FlexPanel() {
    init {
        marginBottom = 12.px

        // TODO: Add date range input

        val exportBtn = button(
            text = "Export Log"
        ) {
            margin = auto
        }
        exportBtn.enableTooltip(TooltipOptions(
            title = "Exports the currently shown time entries as a CSV file. Entries are shown in ISO-8601 format.",
            delay = 500,
            hideDelay = 100,
        ))
        exportBtn.onClick {
            exportCSV()
        }
    }

    private fun exportCSV(range: ClosedRange<LocalDate>? = null) {
        val entries = if(range != null) {
            _timecard.filterByDateRange(range)
        } else {
            _timecard.entries
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

fun Container.logActions(timecard: Timecard): LogActions {
    return LogActions(timecard).also { add(it) }
}
