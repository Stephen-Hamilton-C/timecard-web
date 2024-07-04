package com.github.stephenhamiltonc.timecard_web

import com.github.stephenhamiltonc.timecard_web.core.TimecardState
import com.github.stephenhamiltonc.timecard_web.page.credits.CreditsPage
import com.github.stephenhamiltonc.timecard_web.page.home.HomePage
import com.github.stephenhamiltonc.timecard_web.page.settings.SettingsPage
import io.kvision.core.Container
import io.kvision.core.onClick
import io.kvision.panel.SideTabSize
import io.kvision.panel.TabPosition
import io.kvision.panel.VPanel
import io.kvision.panel.tabPanel
import io.kvision.utils.px
import io.kvision.utils.vh

class Menu : VPanel(className = "tc-menu") {
    init {
        height = 100.vh

        tabPanel(
            tabPosition = TabPosition.LEFT,
            sideTabSize = SideTabSize.SIZE_1
        ) {
            marginLeft = 6.px
            marginRight = 6.px
            height = 100.vh

            addTab("Home", HomePage(), route = "/")
            addTab("Settings", SettingsPage(), route = "/settings")
            addTab("Credits", CreditsPage(), route = "/credits")
        }.getTabs().forEach {
            it.onClick {
                TimecardState.reload()
            }
        }
    }
}

fun Container.menu(): Menu {
    return Menu().also { add(it) }
}
