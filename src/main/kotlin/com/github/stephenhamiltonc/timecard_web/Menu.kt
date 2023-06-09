package com.github.stephenhamiltonc.timecard_web

import com.github.stephenhamiltonc.timecard_web.page.home.HomePage
import com.github.stephenhamiltonc.timecard_web.page.settings.SettingsPage
import io.kvision.core.Container
import io.kvision.panel.SideTabSize
import io.kvision.panel.TabPosition
import io.kvision.panel.VPanel
import io.kvision.panel.tabPanel
import io.kvision.utils.px

class Menu : VPanel(className = "tc-menu") {
    init {
        tabPanel(
            tabPosition = TabPosition.LEFT,
            sideTabSize = SideTabSize.SIZE_1
        ) {
            marginLeft = 6.px
            marginRight = 6.px

            addTab("Home", HomePage(), route = "/")
            addTab("Settings", SettingsPage(), route = "/settings")
        }
    }
}

fun Container.menu(): Menu {
    return Menu().also { add(it) }
}
