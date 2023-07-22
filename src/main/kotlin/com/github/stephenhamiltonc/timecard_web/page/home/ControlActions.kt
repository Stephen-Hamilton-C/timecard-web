package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import com.github.stephenhamiltonc.timecard_web.core.settings.Persistence
import io.kvision.core.Container
import io.kvision.core.JustifyContent
import io.kvision.form.time.DateTimeInput
import io.kvision.html.button
import io.kvision.panel.HPanel
import io.kvision.toolbar.buttonGroup
import io.kvision.utils.px
import kotlinx.datetime.*
import kotlin.js.Date

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

        // TODO: Icons are missing... something to do with FontAwesome?
        val picker = DateTimeInput() {
            // Set maxDate to a minute in the future so the current time can be selected
            maxDate = Date(Date.now() + 60000)
            if(Persistence.militaryTime) {
                // TODO: HH:mm still shows 12-hour time... is this a bug?
                // hh:mm shows 12-hour time too
                // Also, time picker still shows 12-hour input even if this is 24-hour
                // Honestly, I might just contribute to KVision with these fixes
                // This, along with a property to determine if the popup is open or not, along with an event
                format = "YYYY-MM-DD H:mm"
            }
            width = 0.px
            height = 0.px
            zIndex = -1
            setStyle("transform", "translate(-306px, 44px)")
        }

        buttonGroup() {
            val clockClassName = if (timecard.isClockedIn) {
                "btn-danger"
            } else {
                "btn-success"
            }

            // TODO: Figure out how to bind to DateTime
            val clockDateText = if (picker.value == null) {
                ""
            } else {
                " at ${picker.value}"
            }

            button(
                text = "Clock $clockText",
                className = clockClassName
            ).onClick {
                val clockTime = picker.value?.toKotlinInstant() ?: Clock.System.now()
                if (timecard.isClockedIn) {
                    timecard.clockOut(clockTime)
                } else {
                    timecard.clockIn(clockTime)
                }

                picker.value = null
                TimecardState.save()
            }

            button(
                text = "",
                className = "$clockClassName bi bi-chevron-down"
            ).onClick {
                it.stopPropagation()
                picker.togglePopup()
            }
        }

        add(picker)
    }
}

fun Container.controlActions(timecard: Timecard): ControlActions {
    return ControlActions(timecard).also { add(it) }
}
