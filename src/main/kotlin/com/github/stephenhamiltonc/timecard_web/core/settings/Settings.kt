package com.github.stephenhamiltonc.timecard_web.core.settings

import kotlinx.serialization.builtins.serializer

private const val darkThemeKey = "settings.dark"
private const val militaryTimeKey = "settings.militaryTime"
private const val entryLifespanKey = "settings.entryLifespan"
private const val timeFormatKey = "settings.timeFormat"
private const val minutesInWorkDayKey = "settings.minutesInWorkDay"

object Settings {
    // TODO: Implement
    var darkTheme by LocalStorageProperty(darkThemeKey, true, Boolean.serializer())
    var militaryTime by LocalStorageProperty(militaryTimeKey, false, Boolean.serializer())
    // TODO: Implement
    var entryLifespan by LocalStorageProperty(entryLifespanKey, EntryLifespan.ONE_MONTH, EntryLifespan.serializer())
    var timeFormat by LocalStorageProperty(timeFormatKey, TimeFormat.QUARTER_HOUR, TimeFormat.serializer())
    var minutesInWorkDay by LocalStorageProperty(minutesInWorkDayKey, (8 * 60), Long.serializer())
}
