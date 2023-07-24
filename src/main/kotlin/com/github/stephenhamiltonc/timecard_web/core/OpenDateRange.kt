package com.github.stephenhamiltonc.timecard_web.core

import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class OpenDateRange(val start: LocalDate? = null, val end: LocalDate? = null) {
    private val _definedStart by lazy {
        Instant.DISTANT_PAST.toLocalDateTime(TimeZone.currentSystemDefault()).date
    }
    private val _definedEnd by lazy {
        Instant.DISTANT_FUTURE.toLocalDateTime(TimeZone.currentSystemDefault()).date
    }

    fun isDefined() = start != null || end != null

    fun toClosedRange(): ClosedRange<LocalDate> {
        return if(start != null && end != null) {
            start..end
        } else if(start != null && end == null) {
            start.._definedEnd
        } else if(start == null && end != null) {
            _definedStart..end
        } else {
            _definedStart.._definedEnd
        }
    }
}
