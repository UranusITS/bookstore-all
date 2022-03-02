package component

import kotlinx.css.*
import styled.StyleSheet

object BookItemStyles:StyleSheet(name = "BookItemStyles", isStatic = true) {
    val frame by css {
        marginTop = 18.px
        marginBottom = 18.px
        marginLeft = 18.px
        marginRight = 18.px
    }
    val price by css {
        color = Color.red
        textAlign = TextAlign.right
        fontSize = 18.pt
        marginBottom = 0.px
    }
    val inventory by css {
        color = rgba(0, 0, 0, 0.45)
        textAlign = TextAlign.right
        fontSize = 8.pt
        marginBottom = 0.px
    }
}
