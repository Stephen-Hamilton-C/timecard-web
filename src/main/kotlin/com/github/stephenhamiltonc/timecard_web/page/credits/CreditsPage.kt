package com.github.stephenhamiltonc.timecard_web.page.credits

import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.utils.px

class CreditsPage : SimplePanel() {
    init {
        marginTop = 12.px

        creditCard(
            project = "timecard-web",
            author = "Stephen Hamilton",
            license = "GPL-3.0",
            website = "https://github.com/Stephen-Hamilton-C/timecard-web",
        )
        div(className = "mt-3")
        creditCard(
            project = "timecard-lib",
            author = "Stephen Hamilton",
            license = "GPL-3.0",
            website = "https://github.com/Stephen-Hamilton-C/timecard-lib",
        )
        div(className = "mt-3")
        creditCard(
            project = "kotlinx.serialization",
            license = "Apache-2.0",
            website = "https://github.com/Kotlin/kotlinx.serialization"
        )
        div(className = "mt-3")
        creditCard(
            project = "KVision",
            author = "Robert Jaros",
            license = "MIT",
            website = "https://github.com/rjaros/kvision"
        )
    }
}