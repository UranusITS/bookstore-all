package component

import antd.carousel.carousel
import component.style.SaleCarouselStyles
import data.SaleCarouselProps
import react.dom.div
import react.dom.img
import react.fc
import styled.css
import styled.styledDiv


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
