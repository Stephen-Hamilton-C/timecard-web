package com.github.stephenhamiltonc.timecard_web.page.credits

import io.kvision.html.div
import io.kvision.panel.SimplePanel
import io.kvision.utils.px

class CreditsPage : SimplePanel() {
    init {
        marginTop = 12.px

        val credits = listOf(
            CreditData(
                project = "timecard-web",
                author = "Stephen Hamilton",
                license = "GPL-3.0",
                website = "https://github.com/Stephen-Hamilton-C/timecard-web",
            ),
            CreditData(
                project = "timecard-lib",
                author = "Stephen Hamilton",
                license = "GPL-3.0",
                website = "https://github.com/Stephen-Hamilton-C/timecard-lib",
            ),
            CreditData(
                project = "kotlinx.serialization",
                author = "Jetbrains",
                license = "Apache-2.0",
                website = "https://github.com/Kotlin/kotlinx.serialization",
            ),
            CreditData(
                project = "KVision",
                author = "Robert Jaros",
                license = "MIT",
                website = "https://github.com/rjaros/kvision",
            ),
            CreditData(
                project = "Bootswatch Darkly",
                author = "Thomas Park",
                license = "MIT",
                website = "https://github.com/thomaspark/bootswatch/",
            ),
            CreditData(
                project = "Bootswatch Flatly",
                author = "Thomas Park",
                license = "MIT",
                website = "https://github.com/thomaspark/bootswatch/"
            ),
        )

        credits.forEach { data ->
            creditCard(data)
            div(className = "mt-3")
        }
    }
}