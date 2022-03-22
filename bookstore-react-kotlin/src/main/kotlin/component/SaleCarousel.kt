package component

import antd.carousel.carousel
import data.BookProps
import data.UserProps
import react.*
import react.dom.div
import react.dom.img
import styled.css
import styled.styledDiv

external interface SaleCarouselProps : Props {
    var sales: List<String>
}

val SaleCarousel = fc<SaleCarouselProps> { props ->
    styledDiv {
        css { +SaleCarouselStyles.main }
        carousel {
            attrs.autoplay = true
            attrs.dotPosition = "Bottom"
            for (sale in props.sales) {
                div {
                    img { attrs.src = sale }
                }
            }
        }
    }
}
