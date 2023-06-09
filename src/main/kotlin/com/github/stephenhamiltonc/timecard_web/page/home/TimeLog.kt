package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.TimeEntries
import com.github.stephenhamiltonc.timecard_web.card
import com.github.stephenhamiltonc.timecard_web.core.TimeEntriesState
import com.github.stephenhamiltonc.timecard_web.core.format
import com.github.stephenhamiltonc.timecard_web.core.formatWithDate
import io.kvision.core.Container
import io.kvision.core.TextAlign
import io.kvision.html.*
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import io.kvision.utils.px

class TimeLog(timeEntries: TimeEntries) : SimplePanel() {
    init {
        marginBottom = 12.px

        if(timeEntries.entries.isNotEmpty()) {
            card() {
                padding = 12.px
                paddingBottom = 0.px

                table(className = "table table-striped") {
                    tbody {
                        for (entry in timeEntries.entries) {
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

fun Container.timeLog(timeEntries: TimeEntries): TimeLog {
    return TimeLog(timeEntries).also { add(it) }
}
