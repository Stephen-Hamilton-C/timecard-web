package com.github.stephenhamiltonc.timecard_web.core

import com.github.stephenhamiltonc.timecard.Timecard
import com.github.stephenhamiltonc.timecard_web.core.settings.Persistence
import io.kvision.state.ObservableValue
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.datetime.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private const val timecardStorageKey = "timecard"

object TimecardState {
    private var _timecard = Timecard()
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


        // Clean up old entries
        val currentDate = Clock.System.now()
            .toLocalDateTime(TimeZone.currentSystemDefault())
            .date
        val oldestDate = currentDate.minus(Persistence.entryLifespan.days, DateTimeUnit.DAY)
        _timecard.clean(oldestDate)

        onModified.setState(_timecard)
        return success
    }

    fun save() {
        val timecardData = Json.encodeToString(_timecard)
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