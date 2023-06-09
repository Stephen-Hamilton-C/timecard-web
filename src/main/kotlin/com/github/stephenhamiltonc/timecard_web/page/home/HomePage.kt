package com.github.stephenhamiltonc.timecard_web.page.home

import com.github.stephenhamiltonc.timecard_web.core.TimeEntriesState
import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import io.kvision.utils.px

class HomePage : SimplePanel("tc-home") {
    init {
        marginTop = 12.px

        bind(TimeEntriesState.onModified) { timeEntries ->
            controls(timeEntries)
            div(className = "my-4")
            timeLog(timeEntries)
        }
    }
}
