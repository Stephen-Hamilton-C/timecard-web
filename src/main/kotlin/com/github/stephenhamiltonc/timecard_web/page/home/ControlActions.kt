package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import io.kvision.core.Container
import io.kvision.core.JustifyContent
import io.kvision.html.button
import io.kvision.panel.HPanel
import io.kvision.toolbar.buttonGroup
import io.kvision.utils.px

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

        buttonGroup {
            val clockClassName = if (timecard.isClockedIn) {
                "btn-danger"
            } else {
                "btn-success"
            }

            button(
                text = "Clock $clockText",
                className = clockClassName
            ).onClick {
                if (timecard.isClockedIn) {
                    timecard.clockOut()
                } else {
                    timecard.clockIn()
                }

                TimecardState.save()
            }

            button(
                text = "",
                className = "$clockClassName bi bi-chevron-down"
            ).onClick {
                println("Open")
            }
        }

    }
}

fun Container.controlActions(timecard: Timecard): ControlActions {
    return ControlActions(timecard).also { add(it) }
}
