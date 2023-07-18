package com.github.stephenhamiltonc.timecard_web.core

import kotlinx.datetime.*
import com.github.stephenhamiltonc.timecard_web.core.settings.Settings
import com.github.stephenhamiltonc.timecard_web.core.settings.TimeFormat

/**
 * Converts the given 24-hour value to a 12-hour value
 */
private fun formatHour(hour: Int): Int {
    return if(Settings.militaryTime) {
        hour
    } else {
        if(hour == 0) {
            12
        } else if(hour > 12) {
            hour - 12
        } else {
            hour
        }
    }
}

/**
 * Gets the meridiem string for the given 24-hour value, if applicable
 */
private fun getMeridiem(hour: Int): String {
    return if(!Settings.militaryTime) {
        if(hour >= 12) {
            " PM"
        } else {
            " AM"
        }
    } else {
        ""
    }
}

fun Long.formatMinutes(format: TimeFormat = Settings.timeFormat): String {
    return format.formatter(this)
}

fun Instant.formatWithDate(): String {
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val truncatedDateTime = LocalDateTime(datetime.year, datetime.month, datetime.dayOfMonth, formatHour(datetime.hour), datetime.minute)
    val meridiem = getMeridiem(datetime.hour)
    return "${truncatedDateTime.toString().replace('T', ' ')}$meridiem"
}

fun Instant.format(): String {
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val time = datetime.time
    val truncatedTime = LocalTime(formatHour(time.hour), time.minute)
    val meridiem = getMeridiem(time.hour)
    return "$truncatedTime$meridiem"
}

fun Long.separateHoursMinutes(): Pair<Int, Int> {
    val hours = (this / 60.0).toInt()
    val minutes = this.mod(60)
    return Pair(hours, minutes)
}
