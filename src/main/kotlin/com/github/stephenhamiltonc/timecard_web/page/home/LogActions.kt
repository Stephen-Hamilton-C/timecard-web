package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.core.OpenDateRange
import downloadAsFile
import io.kvision.core.Container
import io.kvision.core.TooltipOptions
import io.kvision.core.enableTooltip
import io.kvision.form.time.dateTime
import io.kvision.html.button
import io.kvision.panel.FlexPanel
import io.kvision.state.ObservableState
import io.kvision.utils.auto
import io.kvision.utils.px
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toKotlinInstant
import kotlinx.datetime.toLocalDateTime

class LogActions(private val _timecard: Timecard) : FlexPanel(), ObservableState<OpenDateRange> {
    private var _logDateRange = OpenDateRange()
        set(value) {
            field = value
            _observers.forEach { it(value) }
        }
    private val _observers = mutableListOf<(OpenDateRange) -> Unit>()

    init {
        marginBottom = 12.px

        val fromPicker = dateTime {
            label = "Show logs from"
            placeholder = "Forever ago"
            format = "YYYY-MM-DD"
        }
        fromPicker.input.input.subscribe {
            val newStart = fromPicker.value?.toKotlinInstant()?.toLocalDateTime(TimeZone.currentSystemDefault())?.date
            _logDateRange = _logDateRange.copy(start = newStart)
        }

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

        val toPicker = dateTime {
            label = "Show logs up to"
            placeholder = "Today"
            format = "YYYY-MM-DD"
        }
        toPicker.input.input.subscribe {
            val newEnd = toPicker.value?.toKotlinInstant()?.toLocalDateTime(TimeZone.currentSystemDefault())?.date
            _logDateRange = _logDateRange.copy(end = newEnd)
        }
    }

    private fun exportCSV() {
        val entries = if(_logDateRange.isDefined()) {
            _timecard.filterByDateRange(_logDateRange.toClosedRange())
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

        val fileName = if(_logDateRange.isDefined()) {
            val fromText = if(_logDateRange.start != null) {
                "from ${_logDateRange.start}"
            } else {
                ""
            }
            val toText = if(_logDateRange.end != null) {
                val space = if(fromText.isNotBlank()) " " else ""
                "${space}to ${_logDateRange.end}"
            } else {
                ""
            }

            "Timecard Log $fromText$toText.csv"
        } else {
            "Timecard Log.csv"
        }
        csvData.downloadAsFile("text/csv", fileName)
    }

    override fun getState(): OpenDateRange {
        return _logDateRange
    }

    override fun subscribe(observer: (OpenDateRange) -> Unit): () -> Unit {
        _observers.add(observer)
        observer(_logDateRange)
        return {
            _observers.remove(observer)
        }
    }
}

fun Container.logActions(timecard: Timecard): LogActions {
    return LogActions(timecard).also { add(it) }
}
