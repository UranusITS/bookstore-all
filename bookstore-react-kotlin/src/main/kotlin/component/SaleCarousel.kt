package component

import antd.carousel.carousel
import react.Props
import react.RBuilder
import react.RComponent
import react.State
import react.dom.div
import react.dom.img
import styled.css
import styled.styledDiv

external interface SaleCarouselProps : Props {
    var sales: List<String>
}

data class SaleCarouselState(val sales: List<String>) : State

class SaleCarousel (props: SaleCarouselProps) : RComponent<SaleCarouselProps, SaleCarouselState>(props) {
    init {
        state = SaleCarouselState(props.sales)
    }

    override fun RBuilder.render() {
        styledDiv {
            css { +SaleCarouselStyles.main }
            carousel {
                attrs.autoplay = true
                attrs.dotPosition = "Bottom"
                for (sale in state.sales) {
                    div {
                        img { attrs.src = sale }
                    }
                }
            }
        }
    }
}
