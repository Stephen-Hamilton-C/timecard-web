package com.github.stephenhamiltonc.timecard_web.core

import com.github.stephenhamiltonc.timecard.Timecard
import io.kvision.state.ObservableValue
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val timecardStorageKey = "timecard"

object TimecardState {
    var _timecard: Timecard = Timecard()
    val onModified = ObservableValue(_timecard)

    init {
        window.setInterval({
            onModified.setState(_timecard)
            println("Interval update")
        }, 60000)
    }

    fun load(): Boolean {
        var success = true
        val timecardData = localStorage.getItem(timecardStorageKey)
        _timecard = try {
            if(timecardData != null) {
                Json.decodeFromString<Timecard>(timecardData)
            } else {
                Timecard()
            }
        } catch(e: Exception) {
            console.error("Could not read Timecard! Timecard data:\n$timecardData")
            success = false
            Timecard()
        }

        onModified.setState(_timecard)
        return success
    }

    fun save() {
        val timecardData = Json.encodeToString(_timecard)
        println("Saving Timecard as $timecardData")
        localStorage.setItem(timecardStorageKey, timecardData)

        onModified.setState(_timecard)
    }

    fun reload() {
        println("Reloading state...")
        onModified.setState(_timecard)
    }

    fun import(timecard: Timecard) {
        _timecard = timecard
        onModified.setState(_timecard)
    }
}