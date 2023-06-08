package com.github.stephenhamiltonc.timecard_web

import com.github.stephenhamiltonc.timecard_web.core.TimeEntriesState
import io.kvision.*
import io.kvision.html.button
import io.kvision.html.span
import io.kvision.panel.root
import io.kvision.panel.vPanel
import io.kvision.state.bind
import io.kvision.utils.perc
import io.kvision.utils.px
import kotlin.math.roundToInt

class App : Application() {
    private fun getQuarterHours(minutes: Long): Double {
        return ((minutes / 15.0).roundToInt() * 15.0) / 60.0
    }

    override fun start() {
        TimeEntriesState.load()

        root("kvapp").bind(TimeEntriesState.onModified) { timeEntries ->
            vPanel(spacing = 12) {
                marginLeft = 12.px
                marginTop = 12.px
                marginRight = 75.perc

                val clockText = if (timeEntries.isClockedIn) {
                    "out"
                } else {
                    "in"
                }
                button("Undo").onClick {
                    timeEntries.undo()
                    TimeEntriesState.save()
                }

                button("Clock $clockText").onClick {
                    if (timeEntries.isClockedIn) {
                        timeEntries.clockOut()
                    } else {
                        timeEntries.clockIn()
                    }

                    TimeEntriesState.save()
                }

                vPanel {
                    val minutesWorked = timeEntries.calculateMinutesWorked()
                    val quarterHoursWorked = getQuarterHours(minutesWorked)
                    span("Hours worked: $quarterHoursWorked")

                    val minutesOnBreak = timeEntries.calculateMinutesOnBreak()
                    val quarterHoursOnBreak = getQuarterHours(minutesOnBreak)
                    span("Hours on break: $quarterHoursOnBreak")

                    val expectedEndTime = timeEntries.calculateExpectedEndTime(8 * 60)
                    span("Expected end time: $expectedEndTime")
                }

                vPanel {
                    for (entry in timeEntries.filterByDay()) {
                        vPanel {
                            span("IN: ${entry.start}")
                            if (entry.end != null) {
                                span("OUT: ${entry.end}")
                            }
                        }
                    }
                }
            }
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        ToastifyModule,
        BootstrapIconsModule,
        TabulatorModule,
        TabulatorCssBootstrapModule,
        CoreModule
    )
}
