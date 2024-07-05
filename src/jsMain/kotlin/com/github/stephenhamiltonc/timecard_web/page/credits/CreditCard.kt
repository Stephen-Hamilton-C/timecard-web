package com.github.stephenhamiltonc.timecard_web.page.credits

import com.github.stephenhamiltonc.timecard_web.card
import io.kvision.core.Container
import io.kvision.core.PosFloat
import io.kvision.html.br
import io.kvision.html.link
import io.kvision.panel.SimplePanel

// Heheh, like the play on words?
class CreditCard(data: CreditData) : SimplePanel() {
    init {
        card(title = data.project) {
            if(data.author != null) {
                +"Written by ${data.author}"
                br()
            }

            +"Protected under the ${data.license} license"
            br()
            link(label = "Source Code", url = data.website, className = "btn btn-outline-primary") {
                target = "_blank"
                setAttribute("rel", "noopener noreferrer")

                float = PosFloat.RIGHT
            }
        }
    }
}

fun Container.creditCard(data: CreditData): CreditCard {
    return CreditCard(data).also { add(it) }
}
