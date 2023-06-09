package com.github.stephenhamiltonc.timecard_web.core

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.math.roundToInt

fun Long.formatMinutes(): String {
    // TODO: This should be controlled by a setting
    val quarterHoursWorked = ((this / 15.0).roundToInt() * 15.0) / 60.0
    val s = if(quarterHoursWorked != 1.0) "s" else ""
    return "$quarterHoursWorked hour$s"
}

fun Instant.format(): String {
    // TODO: This should be controlled by a setting
    val datetime = this.toLocalDateTime(TimeZone.currentSystemDefault())
    val time = datetime.time
    val truncatedTime = LocalTime(time.hour, time.minute)
    return truncatedTime.toString()
}
