package component.style

import kotlinx.css.*
import styled.StyleSheet

object BookDetailStyles:StyleSheet(name = "BookDetailStyles", isStatic = true) {
    val frame by css {
        width = 1080.px
        margin = "120px auto"
    }
    val bookName by css {
        fontSize = 24.pt
        fontWeight = FontWeight.bold
        marginBottom = 48.px
    }
    val buttons by css {
        marginTop = 120.px
    }
    val button by css {
        width = 240.px
        margin = "0 auto"
    }
}