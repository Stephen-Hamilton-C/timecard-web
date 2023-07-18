package com.github.stephenhamiltonc.timecard_web.core

import io.kvision.toast.ToastOptions
import io.kvision.toast.ToastPosition

val successToastOptions = ToastOptions(
    position = ToastPosition.BOTTOMRIGHT
)
val dangerToastOptions = ToastOptions(
    position = ToastPosition.BOTTOMRIGHT,
    close = true,
    duration = 0,
)
