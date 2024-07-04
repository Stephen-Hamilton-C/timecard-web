package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.core.format
import com.github.stephenhamiltonc.timecard_web.core.formatMinutes
import com.github.stephenhamiltonc.timecard_web.core.persistence.Persistence
import com.github.stephenhamiltonc.timecard_web.core.persistence.TimeFormat
import com.github.stephenhamiltonc.timecard_web.today
import io.kvision.core.Container
import io.kvision.core.Placement
import io.kvision.core.TooltipOptions
import io.kvision.core.enableTooltip
import io.kvision.html.br
import io.kvision.html.span
import io.kvision.panel.SimplePanel
import kotlinx.datetime.LocalDate

fun Container.daySummary(timecard: Timecard, day: LocalDate = LocalDate.today()): DaySummary {
    return DaySummary(timecard, day).also { add(it) }
}

class DaySummary(timecard: Timecard, day: LocalDate = LocalDate.today()) : SimplePanel() {
    init {
        val minutesWorked = timecard.calculateMinutesWorked(day)
        val timeWorkedSpan = span {
            +"Time worked: "
            span(minutesWorked.formatMinutes(), className = "text-success")
        }
        if(Persistence.timeFormat.tooltip) {
            timeWorkedSpan.enableTooltip(
                TooltipOptions(
                title = minutesWorked.formatMinutes(TimeFormat.HOURS_MINUTES),
                delay = 500,
                hideDelay = 100,
                placement = Placement.RIGHT,
            )
            )
        }
        br()

        val minutesOnBreak = timecard.calculateMinutesOnBreak(day)
        val timeOnBreakSpan = span {
            +"Time on break: "
            span(minutesOnBreak.formatMinutes(), className = "text-danger")
        }
        if(Persistence.timeFormat.tooltip) {
            timeOnBreakSpan.enableTooltip(
                TooltipOptions(
                title = minutesOnBreak.formatMinutes(TimeFormat.HOURS_MINUTES),
                delay = 500,
                hideDelay = 100,
                placement = Placement.RIGHT,
            )
            )
        }
        br()

        span {
            +"Expected end time: "
            val expectedEndTime = timecard.calculateExpectedEndTime(Persistence.minutesInWorkDay)
            span(expectedEndTime?.format())
        }
        br()
    }
}
