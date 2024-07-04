package com.github.stephenhamiltonc.timecard_web.core.persistence

import kotlin.math.roundToInt
import com.github.stephenhamiltonc.timecard_web.core.separateHoursMinutes
import io.kvision.core.StringPair
import kotlinx.serialization.Serializable
import pluralize

@Serializable
enum class TimeFormat(val displayName: String, val tooltip: Boolean, val formatter: (Long) -> String) {
    QUARTER_HOUR(
        "Quarter Hour",
        true,
        { totalMinutes: Long ->
            val quarterHoursWorked = ((totalMinutes / 15.0).roundToInt() * 15.0) / 60.0
            "$quarterHoursWorked hours"
        }
    ),
    HALF_HOUR(
        "Half Hour",
        true,
        { totalMinutes: Long -> 
            val halfHoursWorked = ((totalMinutes / 30.0).roundToInt() * 30.0) / 60.0
            "$halfHoursWorked hours"
        }
    ),
    HOURS_MINUTES(
        "X hours, Y minutes",
        false,
        { totalMinutes: Long -> 
            val (hours, minutes) = totalMinutes.separateHoursMinutes()
            "$hours ${"hour".pluralize(hours)}, $minutes ${"minute".pluralize(minutes)}"
        }
    ),
    HR_MIN(
        "X hrs, Y mins",
        false,
        { totalMinutes: Long -> 
            val (hours, minutes) = totalMinutes.separateHoursMinutes()
            "$hours ${"hr".pluralize(hours)}, $minutes ${"min".pluralize(minutes)}"
        }
    ),
    CLOCK(
        "hh:mm",
        false,
        { totalMinutes: Long -> 
            val (hours, minutes) = totalMinutes.separateHoursMinutes()
            val paddedHours = hours.toString().padStart(2, '0')
            val paddedMinutes = minutes.toString().padStart(2, '0')
            "$paddedHours:$paddedMinutes"
        }
    );

    companion object {
        fun getElements(): List<StringPair> {
            return entries.map { it.toString() to it.displayName }
        }

        fun valueOfOrNull(type: String?): TimeFormat? {
            if(type == null) return null
            return try {
                valueOf(type)
            } catch(_: Exception) {
                // Somehow, valueOf can also throw an IllegalStateException,
                // despite documentation only mentioning IllegalArgument
                null
            }
        }
    }
}
