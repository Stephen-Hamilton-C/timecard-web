package com.github.stephenhamiltonc.timecard_web.core

import com.github.stephenhamiltonc.timecard.TimeEntries
import io.kvision.state.ObservableValue
import kotlinx.browser.localStorage
import kotlin.browser.window

private const val entriesStorageKey = "timeentries"

object TimeEntriesState {
    var _timeEntries: TimeEntries = TimeEntries()
    val onModified = ObservableValue(_timeEntries)

    init {
        window.setInterval({
            onModified.setState(_timeEntries)
        }, 60000)
    }

    fun load() {
        val entriesData = localStorage.getItem(entriesStorageKey)
        _timeEntries = try {
            if(entriesData != null) {
                TimeEntries.fromString(entriesData)
            } else {
                TimeEntries()
            }
        } catch(e: Exception) {
            console.error("Could not read TimeEntries! TimeEntries data: $entriesData")
            TimeEntries()
        }

        onModified.setState(_timeEntries)
    }

    fun save() {
        val entriesData = _timeEntries.toString()
        println("Saving TimeEntries as $entriesData")
        localStorage.setItem(entriesStorageKey, entriesData)

        onModified.setState(_timeEntries)
    }
}