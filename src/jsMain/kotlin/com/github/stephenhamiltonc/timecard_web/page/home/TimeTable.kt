package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.TimeEntry
import com.github.stephenhamiltonc.timecard_web.core.format
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.panel.SimplePanel

fun Container.timeTable(entries: Collection<TimeEntry>): TimeTable {
    return TimeTable(entries).also { add(it) }
}

class TimeTable(entries: Collection<TimeEntry>): SimplePanel() {
    init {
        table(className = "table table-striped") {
            tbody {
                for (entry in entries) {
                    tr {
                        td("IN: ${entry.start.format()}")
                        td {
                            entry.end?.let {
                                +"OUT: ${it.format()}"
                            }
                        }
                    }
                }
            }
        }
    }
}