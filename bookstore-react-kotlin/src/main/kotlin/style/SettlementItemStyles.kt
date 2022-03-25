package style

import kotlinx.css.*
import styled.StyleSheet

object SettlementItemStyles:StyleSheet(name = "SettlementItemStyles", isStatic = true) {
    val inline by css {
        display = Display.inlineBlock
    }
    val textInfo by css {
        marginLeft = 24.px
        width = 360.px
    }
    val bookName by css {
        fontWeight = FontWeight.bold
        fontSize = 14.pt
        marginBottom = 8.px
    }
    val author by css {
        color = rgba(0, 0, 0, 0.45)
        fontSize = 10.pt
        marginBottom = 0.px
    }
    val numSelect by css {
        width = 150.px
        textAlign = TextAlign.center
    }
    val numInfo by css {
        fontSize = 12.pt
    }
    val price by css {
        width = 150.px
        textAlign = TextAlign.right
        color = Color.red
        fontSize = 18.pt
    }
    val deleteIcon by css {
        width = 300.px
        textAlign = TextAlign.right
    }
    val bottomMargin by css {
        marginBottom = 0.px
    }
}
