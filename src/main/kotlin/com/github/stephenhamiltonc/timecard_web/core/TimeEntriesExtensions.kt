package com.github.stephenhamiltonc.timecard_web.core

import com.github.stephenhamiltonc.timecard.TimeEntries
import kotlinx.browser.localStorage

private const val entriesStorageKey = "timeentries"

fun TimeEntries.saveToStorage() {
    val entriesData = this.toString()
    localStorage[entriesStorageKey] = entriesData
}

fun TimeEntries.Companion.loadFromStorage() {
    val entriesData = localStorage[entriesStorageKey]
    return if(entriesData != null) {
        TimeEntries.fromString(entriesData)
    } else {
        TimeEntries()
    }
}
