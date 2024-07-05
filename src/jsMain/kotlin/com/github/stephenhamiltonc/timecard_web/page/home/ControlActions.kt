package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import com.github.stephenhamiltonc.timecard_web.core.format
import com.github.stephenhamiltonc.timecard_web.core.formatWithDate
import com.github.stephenhamiltonc.timecard_web.core.persistence.Persistence
import io.kvision.core.*
import io.kvision.form.check.switch
import io.kvision.form.time.DateTimeInput
import io.kvision.form.time.Theme
import io.kvision.form.time.ViewMode
import io.kvision.html.br
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.panel.HPanel
import io.kvision.panel.VPanel
import io.kvision.panel.hPanel
import io.kvision.state.MutableState
import io.kvision.state.bind
import io.kvision.toolbar.buttonGroup
import io.kvision.utils.px
import kotlinx.datetime.*
import kotlin.js.Date

fun Container.controlActions(timecard: Timecard): ControlActions {
    return ControlActions(timecard).also { add(it) }
}

class ControlActions(timecard: Timecard) : VPanel(alignItems = AlignItems.END), MutableState<Boolean> {
    private var _useNow = true
    private val _observers = mutableListOf<(Boolean) -> Unit>()

    init {

        // _useNow should default to false only if there is more than one TimeEntry for today
        val entriesForToday = timecard.filterByDay()
        _useNow = entriesForToday.size <= 1

        switch(
            value = getState(),
            label = "Currently On-break",
        ) {
            disabled = timecard.isClockedIn

            enableTooltip(TooltipOptions(
                title = "Determines whether the current time should be accounted for when calculating time on-break",
                delay = 500,
                hideDelay = 100,
            ))
        }.subscribe {
            setState(it)
        }
        div(className = "my-1")
        br()

        hPanel(justify = JustifyContent.END) {

            val clockText = if (timecard.isClockedIn) "out" else "in"
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

            val lastEntry = timecard.entries.lastOrNull()
            val lastClockTime = lastEntry?.end ?: lastEntry?.start

            val timePicker = DateTimeInput() {
                // Set maxDate to a minute in the future so the current time can be selected
                minDate = lastClockTime?.toJSDate()
                maxDate = Date(Date.now() + 60000)
                theme = if (Persistence.darkTheme) Theme.DARK else Theme.LIGHT
                promptTimeOnDateChange = true
                viewMode = ViewMode.CLOCK

                if (Persistence.militaryTime) {
                    // TODO: HH:mm still shows 12-hour time... is this a bug?
                    // hh:mm shows 12-hour time too
                    // Also, time picker still shows 12-hour input even if this is 24-hour
                    // Honestly, I might just contribute to KVision with these fixes
                    // This, along with a property to determine if the popup is open or not, along with an event
                    format = "YYYY-MM-DD H:mm"
                }
            }

            buttonGroup().bind(timePicker.input) {
                val clockClassName = if (timecard.isClockedIn) {
                    "btn-danger"
                } else {
                    "btn-success"
                }

                val clockTime = timePicker.value?.toKotlinInstant() ?: Clock.System.now()
                val clockDateText = if (timePicker.value == null) {
                    ""
                } else {
                    val now = Clock.System.now()
                    val timeSinceSelectedTime = now - timePicker.value!!.toKotlinInstant()
                    if (timeSinceSelectedTime.inWholeMinutes == 0L) {
                        ""
                    } else {
                        val formattedTime = if (clockTime.daysUntil(now, TimeZone.currentSystemDefault()) > 0) {
                            clockTime.formatWithDate()
                        } else {
                            clockTime.format()
                        }
                        " at $formattedTime"
                    }
                }

                button(
                    text = "Clock $clockText$clockDateText",
                    className = clockClassName
                ).onClick {
                    if (timecard.isClockedIn) {
                        timecard.clockOut(clockTime)
                    } else {
                        timecard.clockIn(clockTime)
                    }

                    timePicker.value = null
                    TimecardState.save()
                }

                button(
                    text = "",
                    className = "$clockClassName bi bi-chevron-down"
                ).onClick {
                    it.stopPropagation()
                    timePicker.togglePopup()
                }
            }

            div {
                width = 0.px
                height = 0.px
                setStyle("visibility", "hidden")
                val translateX = if (timePicker.sideBySide) "-609" else "-306"
                setStyle("transform", "translate(${translateX}px, -24px)")

                add(timePicker)
            }
        }
    }

    override fun getState(): Boolean {
        return _useNow
    }

    override fun setState(state: Boolean) {
        _useNow = state
        _observers.forEach {
            it(state)
        }
    }

    override fun subscribe(observer: (Boolean) -> Unit): () -> Unit {
        _observers += observer
        observer(getState())
        return {
            _observers -= observer
        }
    }
}
