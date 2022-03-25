package style

import kotlinx.css.*
import styled.StyleSheet

object BookItemStyles:StyleSheet(name = "BookItemStyles", isStatic = true) {
    val frame by css {
        marginBottom = 24.px
        marginLeft = 12.px
        marginRight = 12.px
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
