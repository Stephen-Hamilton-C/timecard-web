package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.TimeEntries
import com.github.stephenhamiltonc.timecard_web.card
import com.github.stephenhamiltonc.timecard_web.core.format
import com.github.stephenhamiltonc.timecard_web.core.formatMinutes
import io.kvision.core.Container
import io.kvision.html.br
import io.kvision.html.div
import io.kvision.html.span
import io.kvision.panel.SimplePanel
import io.kvision.utils.px

class Controls(timeEntries: TimeEntries) : SimplePanel() {
    init {
        card() {
            padding = 12.px

            span {
                +"Time worked: "
                val minutesWorked = timeEntries.calculateMinutesWorked()
                span(minutesWorked.formatMinutes(), className = "text-success")
            }
            br()

            span {
                +"Time on break: "
                val minutesOnBreak = timeEntries.calculateMinutesOnBreak()
                span(minutesOnBreak.formatMinutes(), className = "text-danger")
            }
            br()

            span {
                +"Expected end time: "
                val expectedEndTime = timeEntries.calculateExpectedEndTime(8 * 60)
                span(expectedEndTime?.format())
            }
            br()

            div(className = "my-4")

            controlActions(timeEntries)
        }
    }
}

fun Container.controls(timeEntries: TimeEntries): Controls {
    return Controls(timeEntries).also { add(it) }
}
