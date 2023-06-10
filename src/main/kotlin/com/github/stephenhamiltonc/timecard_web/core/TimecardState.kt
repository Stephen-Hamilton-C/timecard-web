package com.github.stephenhamiltonc.timecard_web.core

import com.github.stephenhamiltonc.timecard.Timecard
import io.kvision.state.ObservableValue
import kotlinx.browser.localStorage
import kotlinx.browser.window

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
                Timecard.fromString(timecardData)
            } else {
                Timecard()
            }
        } catch(e: Exception) {
            console.error("Could not read Timecard! Timecard data: $timecardData")
            success = false
            Timecard()
        }

        onModified.setState(_timecard)
        return success
    }

    fun save() {
        val timecardData = _timecard.toString()
        println("Saving Timecard as $timecardData")
        localStorage.setItem(timecardStorageKey, timecardData)

        onModified.setState(_timecard)
    }
}