package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.TimeEntries
import com.github.stephenhamiltonc.timecard_web.core.TimeEntriesState
import io.kvision.core.Container
import io.kvision.core.JustifyContent
import io.kvision.html.button
import io.kvision.panel.HPanel
import io.kvision.utils.px

class ControlActions(timeEntries: TimeEntries) : HPanel(justify = JustifyContent.END) {
    init {
        val clockText = if(timeEntries.isClockedIn) "out" else "in"
        val undoButton = button(
            text = "Undo",
            disabled = timeEntries.entries.isEmpty(),
            className = "btn-secondary",
        ) {
            marginRight = 6.px
        }
        undoButton.onClick {

            timeEntries.undo()
            TimeEntriesState.save()
        }

        button(
            text = "Clock $clockText",
            className = if(timeEntries.isClockedIn) {
                "btn-danger"
            } else {
                "btn-success"
            }
        ).onClick {
            if(timeEntries.isClockedIn) {
                timeEntries.clockOut()
            } else {
                timeEntries.clockIn()
            }

            TimeEntriesState.save()
        }
    }
}

fun Container.controlActions(timeEntries: TimeEntries): ControlActions {
    return ControlActions(timeEntries).also { add(it) }
}
