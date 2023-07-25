package com.github.stephenhamiltonc.timecard_web.core.persistence

import io.kvision.core.StringPair
import kotlinx.serialization.Serializable

@Serializable
enum class EntryLifespan(val days: Int, val displayName: String) {
    ONE_DAY(1, "1 day"),
    THREE_DAYS(3, "3 days"),
    ONE_WEEK(7, "1 week"),
    TWO_WEEKS(14, "2 weeks"),
    ONE_MONTH(30, "1 month"),
    THREE_MONTHS(90, "3 months"),
    NINE_MONTHS(270, "9 months"),
    ONE_YEAR(365, "1 year"),
    FOREVER(-1, "Forever");

    companion object {
        fun getElements(): List<StringPair> {
            return values().map { it.toString() to it.displayName }
        }

        fun valueOfOrNull(type: String?): EntryLifespan? {
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
