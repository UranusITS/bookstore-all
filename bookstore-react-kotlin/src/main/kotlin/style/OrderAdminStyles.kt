package style

import kotlinx.css.*
import styled.StyleSheet

object OrderAdminStyles : StyleSheet(name = "OrderAdminStyles", isStatic = true) {
    val inline by css {
        display = Display.inlineBlock
    }
    val orderId by css {
        marginLeft = 72.px
        width = 120.px
    }
    val username by css {
        marginLeft = 24.px
        width = 120.px
    }
    val address by css {
        marginLeft = 24.px
        width = 300.px
    }
    val infoIcon by css {
        width = 180.px
        textAlign = TextAlign.right
    }
}
