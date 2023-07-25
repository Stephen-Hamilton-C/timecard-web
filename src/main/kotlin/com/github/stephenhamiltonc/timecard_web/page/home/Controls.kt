package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.card
import com.github.stephenhamiltonc.timecard_web.core.format
import com.github.stephenhamiltonc.timecard_web.core.formatMinutes
import com.github.stephenhamiltonc.timecard_web.core.persistence.Persistence
import com.github.stephenhamiltonc.timecard_web.core.persistence.TimeFormat
import io.kvision.core.Container
import io.kvision.core.TooltipOptions
import io.kvision.core.enableTooltip
import io.kvision.core.Placement
import io.kvision.html.br
import io.kvision.html.div
import io.kvision.html.span
import io.kvision.panel.SimplePanel
import io.kvision.utils.px

class Controls(timecard: Timecard) : SimplePanel() {
    init {
        card() {
            padding = 12.px

            val minutesWorked = timecard.calculateMinutesWorked()
            val timeWorkedSpan = span {
                +"Time worked: "
                span(minutesWorked.formatMinutes(), className = "text-success")
            }
            if(Persistence.timeFormat.tooltip) {
                timeWorkedSpan.enableTooltip(TooltipOptions(
                    title = minutesWorked.formatMinutes(TimeFormat.HOURS_MINUTES),
                    delay = 500,
                    hideDelay = 100,
                    placement = Placement.RIGHT,
                ))
            }
            br()

            val minutesOnBreak = timecard.calculateMinutesOnBreak()
            val timeOnBreakSpan = span {
                +"Time on break: "
                span(minutesOnBreak.formatMinutes(), className = "text-danger")
            }
            if(Persistence.timeFormat.tooltip) {
                timeOnBreakSpan.enableTooltip(TooltipOptions(
                    title = minutesOnBreak.formatMinutes(TimeFormat.HOURS_MINUTES),
                    delay = 500,
                    hideDelay = 100,
                    placement = Placement.RIGHT,
                ))
            }
            br()

            span {
                +"Expected end time: "
                val expectedEndTime = timecard.calculateExpectedEndTime(Persistence.minutesInWorkDay)
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
