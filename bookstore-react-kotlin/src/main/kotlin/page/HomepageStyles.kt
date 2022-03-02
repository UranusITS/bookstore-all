package page

import kotlinx.css.Color
import kotlinx.css.backgroundColor
import styled.StyleSheet

object HomepageStyles:StyleSheet(name = "HomepageStyles", isStatic = true) {
    val title by css {
        backgroundColor = Color.blue
    }
}
