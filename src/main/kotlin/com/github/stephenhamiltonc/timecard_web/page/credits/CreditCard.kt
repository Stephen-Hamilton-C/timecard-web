package com.github.stephenhamiltonc.timecard_web.page.credits

import com.github.stephenhamiltonc.timecard_web.card
import io.kvision.core.Container
import io.kvision.core.PosFloat
import io.kvision.html.br
import io.kvision.html.link
import io.kvision.panel.SimplePanel

// Heheh, like the play on words?
class CreditCard(project: String, author: String? = null, license: String, website: String) : SimplePanel() {
    init {
        card(title = project) {
            if(author != null) {
                +"Written by $author"
                br()
            }

            +"Protected under the $license license"
            br()
            link(label = "Source Code", url = website, className = "btn btn-outline-primary") {
                target = "_blank"
                setAttribute("rel", "noopener noreferrer")

                float = PosFloat.RIGHT
            }
        }
    }
}

fun Container.creditCard(project: String, author: String? = null, license: String, website: String): CreditCard {
    return CreditCard(project, author, license, website).also { add(it) }
}
