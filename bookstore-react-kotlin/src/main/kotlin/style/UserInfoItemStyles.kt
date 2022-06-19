package style

import kotlinx.css.*
import styled.StyleSheet

object UserInfoItemStyles : StyleSheet(name = "UserInfoItemStyles", isStatic = true)  {
    val inline by css {
        display = Display.inlineBlock
    }
    val userId by css {
        marginLeft = 24.px
        width = 32.px
    }
    val userName by css {
        marginLeft = 72.px
        width = 128.px
    }
    val authLevel by css {
        marginLeft = 120.px
        width = 240.px
    }
    val editButton by css {
        width = 240.px
        textAlign = TextAlign.right
    }
}