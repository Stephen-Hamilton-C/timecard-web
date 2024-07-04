package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.card
import io.kvision.core.Container
import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.utils.px

class Controls(timecard: Timecard) : SimplePanel() {
    init {
        card {
            padding = 12.px

            daySummary(timecard)

            div(className = "my-4")

            controlActions(timecard)
        }
    }
}

fun Container.controls(timecard: Timecard): Controls {
    return Controls(timecard).also { add(it) }
}
