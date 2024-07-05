package com.github.stephenhamiltonc.timecard_web

import kotlinx.datetime.*

fun LocalDate.Companion.today(): LocalDate {
    return Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
}

fun Instant.toLocalDate(): LocalDate {
    return this.toLocalDateTime(TimeZone.currentSystemDefault()).date
}
