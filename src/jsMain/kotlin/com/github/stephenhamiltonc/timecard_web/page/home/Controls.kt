package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.card
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import io.kvision.utils.px

class Controls(timecard: Timecard) : SimplePanel() {
    init {
        card {
            padding = 12.px

            val actions = ControlActions(timecard)

            // Trying to bind to DaySummary results in it not being drawn...
            // There's gotta be a way to make it bindable,
            // but for the time being, this is fine
            div().bind(actions) {
                daySummary(timecard, useNowForBreak = it)
            }

            div(className = "my-4")

            add(actions)
        }
    }
}

fun Container.controls(timecard: Timecard): Controls {
    return Controls(timecard).also { add(it) }
}
