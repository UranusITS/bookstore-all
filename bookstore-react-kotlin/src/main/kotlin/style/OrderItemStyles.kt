package style

import kotlinx.css.*
import styled.StyleSheet

object OrderItemStyles : StyleSheet(name = "OrderItemStyles", isStatic = true) {
    val inline by css {
        display = Display.inlineBlock
    }
    val textInfo by css {
        marginLeft = 20.px
        width = 300.px
    }
    val bookName by css {
        fontWeight = FontWeight.bold
        fontSize = 12.pt
        marginBottom = 8.px
    }
    val author by css {
        color = rgba(0, 0, 0, 0.45)
        fontSize = 8.pt
        marginBottom = 0.px
    }
    val price by css {
        width = 300.px
        textAlign = TextAlign.right
        fontSize = 16.pt
    }
}
