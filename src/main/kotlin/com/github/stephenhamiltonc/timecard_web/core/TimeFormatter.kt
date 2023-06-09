package com.github.stephenhamiltonc.timecard_web.core

import kotlinx.datetime.*
import kotlin.math.roundToInt

fun Long.formatMinutes(): String {
    // TODO: This should be controlled by a setting
    val quarterHoursWorked = ((this / 15.0).roundToInt() * 15.0) / 60.0
    val s = if(quarterHoursWorked != 1.0) "s" else ""
    return "$quarterHoursWorked hour$s"
}

fun Instant.formatWithDate(): String {
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val truncatedDateTime = LocalDateTime(datetime.year, datetime.month, datetime.dayOfMonth, datetime.hour, datetime.minute)
    return truncatedDateTime.toString().replace('T', ' ')
}

fun Instant.format(): String {
    // TODO: This should be controlled by a setting
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val time = datetime.time
    val truncatedTime = LocalTime(time.hour, time.minute)
    return truncatedTime.toString()
}
