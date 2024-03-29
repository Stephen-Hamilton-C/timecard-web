package com.github.stephenhamiltonc.timecard_web.page.settings

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.Card
import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import com.github.stephenhamiltonc.timecard_web.core.persistentToastOptions
import com.github.stephenhamiltonc.timecard_web.core.persistence.EntryLifespan
import com.github.stephenhamiltonc.timecard_web.core.persistence.Persistence
import com.github.stephenhamiltonc.timecard_web.core.persistence.TimeFormat
import com.github.stephenhamiltonc.timecard_web.core.generalToastOptions
import downloadAsFile
import io.kvision.core.Display
import io.kvision.form.check.switch
import io.kvision.form.number.spinner
import io.kvision.form.select.select
import io.kvision.html.*
import io.kvision.modal.Confirm
import io.kvision.panel.SimplePanel
import io.kvision.state.bind
import io.kvision.toast.Toast
import io.kvision.utils.px
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerializationException
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.files.FileReader

class SettingsPage() : SimplePanel() {
    init {
        marginTop = 12.px

        bind(TimecardState.onModified) {
            add(settingsCard())
            div(className = "mt-4")
            add(dataCard(it))
        }
    }

    private fun settingsCard() = Card(title = "Settings") {
        paddingTop = 12.px

        val darkThemeSwitch = switch(value = Persistence.darkTheme) {
            label = "Dark Theme"
            addCssClass("user-select-none")
        }
        darkThemeSwitch.subscribe {
            Persistence.darkTheme = it
        }
        darkThemeSwitch.onClick {
            window.setTimeout({
                window.location.reload()
            }, 75)
        }

        switch(value = Persistence.militaryTime) {
            label = "24-Hour Time"
            marginBottom = 12.px
            addCssClass("user-select-none")
        }.subscribe {
            Persistence.militaryTime = it
        }

        select(
            label = "Keep entries for",
            options = EntryLifespan.getElements(),
            value = Persistence.entryLifespan.toString()
        ){
            addCssClass("user-select-none")
        }.subscribe {
            Persistence.entryLifespan = EntryLifespan.valueOf(it!!)
        }

        select(
            label = "Time Format",
            options = TimeFormat.getElements(),
            value = Persistence.timeFormat.toString()
        ){
            addCssClass("user-select-none")
        }.subscribe {
            Persistence.timeFormat = TimeFormat.valueOf(it!!)
        }

        spinner(
            label = "Minutes in a Work Day",
            value = Persistence.minutesInWorkDay,
            min = 1,
            max = 1439,
        ){
            addCssClass("user-select-none")
        }.subscribe {
            Persistence.minutesInWorkDay = it!!.toLong()
        }
    }

    private fun dataCard(timecard: Timecard) = Card(title = "Timecard Data") {
        val btnSpacing = 8.px

        button("Import Timecard Data") {
            display = Display.BLOCK
            marginTop = 12.px
            marginBottom = btnSpacing
        }.onClick { importTimecard() }
        button("Export Timecard Data"){
            display = Display.BLOCK
            marginBottom = btnSpacing
        }.onClick { exportTimecard(timecard) }

        button("Import Timecard Settings") {
            display = Display.BLOCK
            marginTop = 24.px
            marginBottom = btnSpacing
        }.onClick { importSettings() }
        button("Export Timecard Settings") {
            display = Display.BLOCK
            marginBottom = btnSpacing
        }.onClick { exportSettings() }

        button(
            text = "Clear Timecard Data",
            style = ButtonStyle.DANGER
        ) {
            marginTop = 24.px
            display = Display.BLOCK
        }.onClick {
            Confirm.show(
                caption = "Clear Timecard Data",
                text = "Are you sure you want to clear <em>all</em> your Timecard data? " +
                        "This action is irreversible, and your data will be gone forever, which is a considerably long time.",
                rich = true,
                centered = true,
                cancelVisible = false,
                yesCallback = {
                    timecard.clear()
                    TimecardState.save()
                },
            )
        }
    }

    private fun openFile(accept: String, onLoad: (String) -> Unit) {
        val input = document.createElement("input") as HTMLInputElement
        input.type = "file"
        input.accept = accept
        input.onchange = {
            input.files?.item(0)?.let { file ->
                val reader = FileReader()
                reader.onload = {
                    onLoad(reader.result as String)
                }
                reader.readAsText(file)
            }
        }
        input.click()
    }

    private fun exportSettings() {
        val settingsData = Persistence.export()
        settingsData.downloadAsFile("application/json", "timecard_settings.json")
        Toast.success(
            "Successfully exported Timecard settings!",
            generalToastOptions
        )
    }

    private fun importSettings() {
        openFile("application/json") { data ->
            try {
                Persistence.import(data)
                window.location.reload()
            } catch(se: SerializationException) {
                // Serialization threw the error due to a decoding error
                Toast.danger(
                    "Error while decoding the provided Timecard settings! Did you select the right file?",
                    persistentToastOptions
                )
            }
        }
    }

    private fun exportTimecard(timecard: Timecard) {
        val timecardData = Json.encodeToString(timecard)
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        timecardData.downloadAsFile("application/json", "timecard_$today.json")
        Toast.success(
            "Successfully exported Timecard data!",
            generalToastOptions
        )
    }

    private fun importTimecard() {
        openFile("application/json") { data ->
            val errorStartText = "Error while decoding the provided Timecard data!"

            try {
                val newTimecard = Json.decodeFromString<Timecard>(data)
                TimecardState.import(newTimecard)
                TimecardState.save()
                println("Finished importing Timecard")
                Toast.success(
                    "Successfully imported Timecard data!",
                    generalToastOptions
                )
            } catch(se: SerializationException) {
                // Serialization threw the error due to a decoding error
                Toast.danger(
                    "$errorStartText Is this from an incompatible version?",
                    persistentToastOptions
                )
            } catch(iae: IllegalArgumentException) {
                // Serialization threw the error due to mismatched Json
                Toast.danger(
                    "$errorStartText Did you select the right file?",
                    persistentToastOptions
                )
            } catch(ise: IllegalStateException) {
                // Timecard Lib threw the error due to non-chronological data
                Toast.danger(
                    "$errorStartText Was the file tampered with?",
                    persistentToastOptions
                )
            }
        }
    }
}
