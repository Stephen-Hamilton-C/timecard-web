package com.github.stephenhamiltonc.timecard_web

import com.github.stephenhamiltonc.timecard_web.page.home.HomePage
import com.github.stephenhamiltonc.timecard_web.page.settings.SettingsPage
import io.kvision.core.Container
import io.kvision.panel.SideTabSize
import io.kvision.panel.TabPosition
import io.kvision.panel.VPanel
import io.kvision.panel.tabPanel

class Menu : VPanel(className = "tc-menu") {
    init {
        tabPanel(
            tabPosition = TabPosition.LEFT,
            sideTabSize = SideTabSize.SIZE_1
        ) {
            addTab("Home", HomePage(), "fa-bars", route = "/")
            addTab("Settings", SettingsPage(), "fa-edit", route = "/settings")
        }
    }
}

fun Container.menu(): Menu {
    return Menu().also { add(it) }
}
