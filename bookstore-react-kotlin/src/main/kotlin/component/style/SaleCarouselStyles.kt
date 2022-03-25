package component.style

import kotlinx.css.*
import styled.StyleSheet

object SaleCarouselStyles:StyleSheet(name = "SaleCarouselStyles", isStatic = true) {
    val main by css {
        margin = "50px auto"
        width = 750.px
    }
}
