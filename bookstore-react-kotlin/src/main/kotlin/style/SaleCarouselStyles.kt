package style

import kotlinx.css.margin
import kotlinx.css.px
import kotlinx.css.width
import styled.StyleSheet

object SaleCarouselStyles : StyleSheet(name = "SaleCarouselStyles", isStatic = true) {
    val main by css {
        margin = "50px auto"
        width = 750.px
    }
}
