package com.github.stephenhamiltonc.timecard_web.core

import kotlinx.datetime.*
import com.github.stephenhamiltonc.timecard_web.core.settings.Settings
import com.github.stephenhamiltonc.timecard_web.core.settings.TimeFormat

fun Long.formatMinutes(format: TimeFormat = Settings.timeFormat): String {
    return format.formatter(this)
}

fun Instant.formatWithDate(): String {
    // TODO: Check for militaryTime before formatting
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val truncatedDateTime = LocalDateTime(datetime.year, datetime.month, datetime.dayOfMonth, datetime.hour, datetime.minute)
    return truncatedDateTime.toString().replace('T', ' ')
}

fun Instant.format(): String {
    // TODO: Check for militaryTime before formatting
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val time = datetime.time
    val truncatedTime = LocalTime(time.hour, time.minute)
    return truncatedTime.toString()
}

fun Long.separateHoursMinutes(): Pair<Int, Int> {
    val hours = (this / 60.0).toInt()
    val minutes = this.mod(60)
    return Pair(hours, minutes)
}
