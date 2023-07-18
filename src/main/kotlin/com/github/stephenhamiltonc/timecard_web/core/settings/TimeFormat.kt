package com.github.stephenhamiltonc.timecard_web.core.settings

import kotlin.math.roundToInt
import com.github.stephenhamiltonc.timecard_web.core.separateHoursMinutes

@Serializable
enum class TimeFormat(val displayName: String, val formatter: (Long) -> String) {
    QUARTER_HOUR(
        "Quarter Hour",
        { totalMinutes: Long ->
            val quarterHoursWorked = ((totalMinutes / 15.0).roundToInt() * 15.0) / 60.0
            "$quarterHoursWorked hours"
        }
    ),
    HALF_HOUR(
        "Half Hour",
        { totalMinutes: Long -> 
            val halfHoursWorked = ((totalMinutes / 30.0).roundToInt() * 30.0) / 60.0
            "$halfHoursWorked hours"
        }
    ),
    HOURS_MINUTES(
        "X hours, Y minutes",
        { totalMinutes: Long -> 
            val (hours, minutes) = totalMinutes.separateHoursMinutes()
            "$hours ${"hour".pluralize(hours)}, $minutes ${"minute".pluralize(minutes)}"
        }
    ),
    HR_MIN(
        "X hrs, Y mins",
        { totalMinutes: Long -> 
            val (hours, minutes) = totalMinutes.separateHoursMinutes()
            "$hours ${"hr".pluralize(hours)}, $minutes ${"min".pluralize(minutes)}"
        }
    ),
    CLOCK(
        "hh:mm",
        { totalMinutes: Long -> 
            val (hours, minutes) = totalMinutes.separateHoursMinutes()
            val paddedHours = hours.toString().padStart(2, '0')
            val paddedMinutes = minutes.toString().padStart(2, '0')
            "$paddedHours:$paddedMinutes"
        }
    );

    companion object {
        fun getDisplayNames(): List<String> {
            return values().map { it.displayName }
        }

        fun valueOfOrNull(type: String?): TimeFormat? {
            if(type == null) return null
            return try {
                valueOf(type)
            } catch(_: IllegalArgumentException) {
                null
            }
        }
    }
}
