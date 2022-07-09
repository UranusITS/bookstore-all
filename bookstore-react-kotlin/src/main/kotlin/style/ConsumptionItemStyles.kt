package style

import kotlinx.css.*
import styled.StyleSheet

object ConsumptionItemStyles : StyleSheet(name = "ConsumptionItemStyles", isStatic = true)  {
    val inline by css {
        display = Display.inlineBlock
    }
    val userId by css {
        marginLeft = 24.px
        width = 32.px
    }
    val username by css {
        marginLeft = 120.px
        width = 128.px
    }
    val consumption by css {
        marginLeft = 120.px
        width = 360.px
        textAlign = TextAlign.right
        color = Color.red
        fontSize = 18.pt
    }
}