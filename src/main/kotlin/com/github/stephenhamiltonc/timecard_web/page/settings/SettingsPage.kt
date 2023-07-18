package com.github.stephenhamiltonc.timecard_web.page.settings

import com.github.stephenhamiltonc.timecard_web.card
import com.github.stephenhamiltonc.timecard_web.core.settings.EntryLifespan
import com.github.stephenhamiltonc.timecard_web.core.settings.Settings
import com.github.stephenhamiltonc.timecard_web.core.settings.TimeFormat
import io.kvision.form.check.checkBox
import io.kvision.form.number.spinner
import io.kvision.form.select.select
import io.kvision.html.ButtonStyle
import io.kvision.html.button
import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.utils.px

class SettingsPage : SimplePanel() {
    init {
        marginTop = 12.px

        card(title = "Settings") {
            paddingTop = 12.px

            checkBox(value = Settings.darkTheme) {
                label = "Dark Theme"
            }.subscribe {
                Settings.darkTheme = it
            }

            checkBox(value = Settings.militaryTime) {
                label = "24-Hour Time"
                marginBottom = 12.px
            }.subscribe {
                Settings.militaryTime = it
            }

            select(
                label = "Keep entries for",
                options = EntryLifespan.getElements(),
                value = Settings.entryLifespan.toString()
            ).subscribe {
                Settings.entryLifespan = EntryLifespan.valueOf(it!!)
            }

            select(
                label = "Time Format",
                options = TimeFormat.getElements(),
                value = Settings.timeFormat.toString()
            ).subscribe {
                Settings.timeFormat = TimeFormat.valueOf(it!!)
            }

            spinner(
                label = "Minutes in a Work Day",
                value = Settings.minutesInWorkDay,
                min = 1,
                max = 1439,
            ).subscribe {
                Settings.minutesInWorkDay = it!!.toLong()
            }
        }

        div(className = "mt-4")

        card(title = "Timecard Data") {
            button("Import Timecard Data")
            button("Export Timecard Data")
            button(
                text = "Clear Timecard Data",
                style = ButtonStyle.DANGER
            )
        }
    }
}
