package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import downloadAsFile
import io.kvision.core.Container
import io.kvision.core.TooltipOptions
import io.kvision.core.enableTooltip
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.utils.auto
import io.kvision.utils.px
import kotlinx.datetime.LocalDate

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

        // TODO: put dates in file name
        csvData.downloadAsFile("text/csv", "Timecard Log.csv")
    }
}

fun Container.logActions(timecard: Timecard): LogActions {
    return LogActions(timecard).also { add(it) }
}
