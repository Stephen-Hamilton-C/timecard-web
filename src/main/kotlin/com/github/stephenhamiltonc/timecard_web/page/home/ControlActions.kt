package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import io.kvision.core.Container
import io.kvision.core.JustifyContent
import io.kvision.html.button
import io.kvision.panel.HPanel
import io.kvision.utils.px
import io.kvision.toolbar.ButtonGroup
import io.kvision.state.ObservableValue
import io.kvision.form.time.DateTimeInput
import kotlinx.datetime.Instant
import kotlinx.datetime.Clock

class ControlActions(timecard: Timecard) : HPanel(justify = JustifyContent.END) {
    init {
        val clockText = if(timecard.isClockedIn) "out" else "in"
        val undoButton = button(
            text = "Undo",
            disabled = timecard.entries.isEmpty(),
            className = "btn-secondary",
        ) {
            marginRight = 6.px
        }
        undoButton.onClick {
            timecard.undo()
            TimecardState.save()
        }

        val clockClassName = if(timecard.isClockedIn) {
            "btn-danger"
        } else {
            "btn-success"
        }

        val clockTimeState: ObservableValue<Instant?> = ObservableValue(null)
        buttonGroup.bind(clockTimeState) { clockTime ->
            val clockTimeText = if(clockTime == null) "" else " at $clockTime"
            button(
                text = "Clock $clockText$clockTimeText",
                className = clockClassName
            ).onClick {
                val time = clockTime ?: Clock.System.now()
                if(timecard.isClockedIn) {
                    timecard.clockOut()
                } else {
                    timecard.clockIn()
                }

                TimecardState.save()
            }

            button(
                // TODO: Replace this with an actual down arrow icon
                text "V",
                className = clockClassName
            ).onClick {
                // Open datetime picker
                println("Open datetime picker")
            }
        }
    }
}

fun Container.controlActions(timecard: Timecard): ControlActions {
    return ControlActions(timecard).also { add(it) }
}
