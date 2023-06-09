package com.github.stephenhamiltonc.timecard_web

import com.github.stephenhamiltonc.timecard_web.core.TimeEntriesState
import io.kvision.*
import io.kvision.panel.root
import io.kvision.routing.Routing

class App : Application() {
    init {
        Routing.init()
    }

    override fun start() {
        TimeEntriesState.load()

        root("kvapp") {
            menu()
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
