package com.github.stephenhamiltonc.timecard_web.core

import io.kvision.toast.ToastOptions
import io.kvision.toast.ToastPosition

val generalToastOptions = ToastOptions(
    position = ToastPosition.BOTTOMRIGHT,
    duration = 5000,
)
val persistentToastOptions = ToastOptions(
    position = ToastPosition.BOTTOMRIGHT,
    close = true,
    duration = 0,
)
