package com.github.stephenhamiltonc.timecard_web.core

import com.github.stephenhamiltonc.timecard.TimeEntries
import kotlinx.browser.localStorage

private const val entriesStorageKey = "timeentries"

fun TimeEntries.saveToStorage() {
    val entriesData = this.toString()
    localStorage.setItem(entriesStorageKey, entriesData)
}

fun TimeEntries.Companion.loadFromStorage(): TimeEntries {
    val entriesData = localStorage.getItem(entriesStorageKey)
    return if(entriesData != null) {
        TimeEntries.fromString(entriesData)
    } else {
        TimeEntries()
    }
}
