package com.github.stephenhamiltonc.timecard_web.core

import kotlinx.datetime.*
import com.github.stephenhamiltonc.timecard_web.core.settings.Settings

fun Long.formatMinutes(): String {
    return Settings.timeFormat.formatter(this)
}

fun Instant.formatWithDate(): String {
    // TODO: Check for militaryTime before formatting
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val truncatedDateTime = LocalDateTime(datetime.year, datetime.month, datetime.dayOfMonth, datetime.hour, datetime.minute)
    return truncatedDateTime.toString().replace('T', ' ')
}

fun Instant.format(): String {
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val time = datetime.time
    val hour = if(Settings.militaryTime) {
        time.hour
    } else {
        if(time.hour == 0) {
            12
        } else if(time.hour > 12) {
            time.hour - 12
        } else {
            time.hour
        }
    }

    val truncatedTime = LocalTime(hour, time.minute)
    val meridiem = if(!Settings.militaryTime) {
        if(time.hour >= 12) {
            " PM"
        } else {
            " AM"
        }
    } else {
        ""
    }
    return "$truncatedTime$meridiem"
}

fun Long.separateHoursMinutes(): Pair<Int, Int> {
    val hours = (this / 60.0).toInt()
    val minutes = this.mod(60)
    return Pair(hours, minutes)
}
