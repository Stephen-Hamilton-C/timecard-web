package com.github.stephenhamiltonc.timecard_web

import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import com.github.stephenhamiltonc.timecard_web.core.persistentToastOptions
import io.kvision.*
import io.kvision.panel.root
import io.kvision.routing.Routing
import io.kvision.toast.Toast

class App : Application() {
    init {
        Routing.init()
    }

    override fun start() {
        val loadSuccess = TimecardState.load()
        if(!loadSuccess) {
            Toast.danger(
                "Error loading Timecard data! Check console for raw data dump.",
                options = persistentToastOptions
            )
        }

        root("kvapp") {
            menu()
            NotificationHandler.requestPermission()
        }
    }
}

fun main() {
    startApplication(
        ::App,
        module.hot,
        BootstrapModule,
        BootstrapCssModule,
        ToastifyModule,
        BootstrapIconsModule,
        TabulatorModule,
        TabulatorCssBootstrapModule,
        CoreModule
    )
}
