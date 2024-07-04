package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.TimeEntry
import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.card
import com.github.stephenhamiltonc.timecard_web.toLocalDate
import com.github.stephenhamiltonc.timecard_web.today
import io.kvision.core.Container
import io.kvision.html.*
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import io.kvision.utils.px
import kotlinx.datetime.LocalDate

fun Container.timeLog(timecard: Timecard): TimeLog {
    return TimeLog(timecard).also { add(it) }
}

class TimeLog(timecard: Timecard) : SimplePanel() {
    init {
        marginBottom = 12.px

        if(timecard.entries.isNotEmpty()) {
            card {
                padding = 12.px

                val actions = logActions(timecard)

                div(className = "accordion").bind(actions) { dateRange ->
                    val entries = if(dateRange.isDefined()) {
                        timecard.filterByDateRange(dateRange.toClosedRange())
                    } else {
                        timecard.entries
                    }

                    val entriesPerDay = mutableMapOf<LocalDate, MutableSet<TimeEntry>>()
                    for (entry in entries.sortedByDescending { it.start }) {
                        val startDay = entry.start.toLocalDate()
                        entriesPerDay.getOrPut(startDay) { mutableSetOf() }.add(entry)

                        entry.end?.let { end ->
                            val endDay = end.toLocalDate()
                            entriesPerDay.getOrPut(endDay) { mutableSetOf() }.add(entry)
                        }
                    }

                    for ((day, entriesForDay) in entriesPerDay) {
                        val isToday = day == LocalDate.today()
                        val collapseId = "time-log_$day"

                        div(className = "accordion-item") {
                            h2(className = "accordion-header") {
                                button(
                                    text = if(isToday) "Today" else day.toString(),
                                    className = "accordion-button",
                                    type = ButtonType.BUTTON
                                ) {
                                    setAttribute("data-bs-toggle", "collapse")
                                    setAttribute("data-bs-target", "#$collapseId")
                                    if(!isToday) {
                                        addCssClass("collapsed")
                                    }
                                }
                            }
                            div(className = "accordion-collapse collapse") {
                                id = collapseId
                                if(isToday) {
                                    addCssClass("show")
                                }
                                div(className = "accordion-body") {
                                    if(!isToday) {
                                        daySummary(timecard, day)
                                        div(className = "my-4")
                                    }
                                    timeTable(entriesForDay)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
