package com.github.stephenhamiltonc.timecard_web.core.settings

import kotlin.properties.Delegates
import kotlinx.browser.localStorage

private const val darkThemeKey = "settings.dark"
private const val militaryTimeKey = "settings.militaryTime"
private const val logLifespanKey = "settings.logLifespan"
private const val timeFormatKey = "settings.timeFormat"
private const val minutesInWorkDayKey = "settings.minutesInWorkDay"

object Settings {
    // TODO: Make a delegate for these properties so that they can be set too
    val darkTheme: Boolean by lazy {
        localStorage.getItem(darkThemeKey).toBoolean() ?: true
    }
    val militaryTime: Boolean by lazy {
        localStorage.getItem(militaryTimeKey).toBoolean() ?: false
    }
    val logLifespan: LogLifespan by lazy {
        LogLifespan.valueOfOrNull(localStorage.getItem(logLifespanKey)) ?: LogLifespan.ONE_MONTH
    }
    val timeFormat: TimeFormat by lazy {
        TimeFormat.valueOfOrNull(localStorage.getItem(timeFormatKey)) ?: TimeFormat.QUARTER_HOUR
    }
    val minutesInWorkDay: Long by lazy {
        localStorage.getItem(minutesInWorkDayKey).toLong() ?: 8 * 60
    }
}
