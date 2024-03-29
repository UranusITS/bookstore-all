package component

import antd.card.card
import antd.card.cardMeta
import data.BookProps
import kotlinext.js.js
import react.buildElement
import react.dom.attrs
import react.dom.img
import react.fc
import react.router.dom.Link
import style.BookItemStyles
import styled.css
import styled.styledDiv
import styled.styledP
import kotlin.math.roundToInt


val BookItemComponent = fc<BookProps> { props ->
    styledDiv {
        css { +BookItemStyles.frame }
        Link {
            attrs.to = "/book/${props.id}"
            card {
                attrs {
                    hoverable = true
                    bordered = false
                    style = js {
                        width = 240
                        paddingTop = 24
                        margin = "0 auto"
                    }
                    cover = buildElement {
                        img {
                            attrs {
                                alt = props.name
                                src = props.imgPath
                            }
                        }
                    }
                }
                cardMeta {
                    attrs {
                        title = props.name
                        description = props.author
                    }
                }
                styledP {
                    css { +BookItemStyles.price }
                    +"￥${(props.price * 100).roundToInt().toDouble() / 100}"
                }
                styledP {
                    css { +BookItemStyles.inventory }
                    +"库存剩余${props.inventory}件"
                }
            }
        }
    }
}
