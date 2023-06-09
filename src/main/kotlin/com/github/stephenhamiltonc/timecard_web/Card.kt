package com.github.stephenhamiltonc.timecard_web

import io.kvision.core.Container
import io.kvision.html.P
import io.kvision.html.div
import io.kvision.html.h5
import io.kvision.html.p
import io.kvision.panel.SimplePanel
import io.kvision.utils.auto
import io.kvision.utils.px

class Card(header: String = "", title: String = "", contentBuilder: (P.() -> Unit)? = null) : SimplePanel("card") {
    init {
        maxWidth = 800.px
        marginLeft = auto
        marginRight = auto

        div(className = "card-body") {
            if(header.isNotEmpty()) {
                div(header, className = "card-header")
            }
            div(className = "card-body") {
                if(title.isNotEmpty()) {
                    h5(title, className = "card-title")
                }
                p(className = "card-text", init = contentBuilder)
            }
        }
    }
}

fun Container.card(header: String = "", title: String = "", contentBuilder: (P.() -> Unit)? = null): Card {
    return Card(header, title, contentBuilder).also { add(it) }
}
