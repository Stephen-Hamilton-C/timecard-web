package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.card
import com.github.stephenhamiltonc.timecard_web.core.formatWithDate
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import io.kvision.utils.px

class TimeLog(timecard: Timecard) : SimplePanel() {
    init {
        marginBottom = 12.px

        if(timecard.entries.isNotEmpty()) {
            card() {
                padding = 12.px
                paddingBottom = 0.px

                val actions = logActions(timecard)

                table(className = "table table-striped").bind(actions) { dateRange ->
                    tbody {
                        // TODO: Make a header for each day

                        val entries = if(dateRange.isDefined()) {
                            timecard.filterByDateRange(dateRange.toClosedRange())
                        } else {
                            timecard.entries
                        }
                        for (entry in entries) {
                            tr {
                                td("IN: ${entry.start.formatWithDate()}")
                                td {
                                    entry.end?.let {
                                        +"OUT: ${it.formatWithDate()}"
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun Container.timeLog(timecard: Timecard): TimeLog {
    return TimeLog(timecard).also { add(it) }
}
