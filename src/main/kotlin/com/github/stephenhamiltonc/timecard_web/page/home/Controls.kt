package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.card
import com.github.stephenhamiltonc.timecard_web.core.format
import com.github.stephenhamiltonc.timecard_web.core.formatMinutes
import com.github.stephenhamiltonc.timecard_web.core.settings.Settings
import io.kvision.core.Container
import io.kvision.html.br
import io.kvision.html.div
import io.kvision.html.span
import io.kvision.panel.SimplePanel
import io.kvision.utils.px

class Controls(timecard: Timecard) : SimplePanel() {
    init {
        card() {
            padding = 12.px

            span {
                +"Time worked: "
                val minutesWorked = timecard.calculateMinutesWorked()
                span(minutesWorked.formatMinutes(), className = "text-success")
            }
            br()

            span {
                +"Time on break: "
                val minutesOnBreak = timecard.calculateMinutesOnBreak()
                span(minutesOnBreak.formatMinutes(), className = "text-danger")
            }
            br()

            span {
                +"Expected end time: "
                val expectedEndTime = timecard.calculateExpectedEndTime(Settings.minutesInWorkDay)
                span(expectedEndTime?.format())
            }
            br()

            div(className = "my-4")

            controlActions(timecard)
        }
    }
}

fun Container.controls(timecard: Timecard): Controls {
    return Controls(timecard).also { add(it) }
}
