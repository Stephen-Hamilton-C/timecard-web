package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import io.kvision.utils.px

class HomePage : SimplePanel("tc-home") {
    init {
        marginTop = 12.px

        bind(TimecardState.onModified) { timecard ->
            controls(timecard)
            div(className = "mt-4")
            timeLog(timecard)
        }
    }
}
