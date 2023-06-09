package com.github.stephenhamiltonc.timecard_web.page.settings

import com.github.stephenhamiltonc.timecard_web.card
import io.kvision.panel.SimplePanel
import io.kvision.utils.px

class SettingsPage : SimplePanel() {
    init {
        marginTop = 12.px

        card(title = "Settings is not yet implemented") {
            +"It will be soon!"
        }
    }
}
