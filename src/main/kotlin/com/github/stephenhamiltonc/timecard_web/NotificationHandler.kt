package com.github.stephenhamiltonc.timecard_web

import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import com.github.stephenhamiltonc.timecard_web.core.generalToastOptions
import com.github.stephenhamiltonc.timecard_web.core.settings.Persistence
import io.kvision.modal.Confirm
import io.kvision.toast.Toast
import kotlinx.datetime.Clock
import org.w3c.notifications.*

object NotificationHandler {
    init {
        TimecardState.onModified.subscribe { timecard ->
            timecard.calculateExpectedEndTime(Persistence.minutesInWorkDay)?.let { expectedEndTime ->
                val now = Clock.System.now()
                if (timecard.isClockedIn && now >= expectedEndTime) {
                    val durationSinceLastNotif = now - Persistence.lastNotified
                    if (durationSinceLastNotif.inWholeMinutes > Persistence.minutesInWorkDay) {
                        Notification(
                            "Time to clock out!",
                            NotificationOptions(body = "You have worked a full day. Don't overdo it! Remember that you always need energy for the next day.")
                        )

                        Persistence.lastNotified = now
                    }
                }
            }
        }
    }

    fun requestPermission() {
        if (Notification.permission == NotificationPermission.DEFAULT) {
            Confirm.show(
                caption = "Notification Permission Notice",
                text = "Timecard Web would like permission to send a notification when you reach your expected end time while clocked in.",
                centered = true,
                cancelVisible = false,
                yesTitle = "Ok",
                noTitle = "Ask me later",
                yesCallback = {
                    Notification.requestPermission().then {
                        if (it != NotificationPermission.GRANTED) {
                            Toast.info(
                                "No notifications will be sent. You can change this setting in your browser's settings.",
                                generalToastOptions
                            )
                        }
                    }
                },
            )
        }
    }
}
