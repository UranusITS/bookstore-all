package component

import antd.card.card
import data.*
import kotlinext.js.js
import kotlinx.css.Color
import kotlinx.css.color
import kotlinx.css.marginLeft
import kotlinx.css.px
import react.*
import style.OrderItemStyles
import styled.*
import kotlin.math.roundToInt


val OrderItemComponent = fc<OrderItemProps> { prop ->
    styledDiv {
        card {
            attrs.bordered = true
            attrs.style = js {
                margin = "0 auto"
                width = 800
            }
            styledDiv {
                css { +OrderItemStyles.inline }
                styledImg {
                    attrs.width = "128px"
                    attrs.src = prop.img_path
                }
            }
            styledDiv {
                css {
                    +OrderItemStyles.inline
                    +OrderItemStyles.textInfo
                }
                styledP {
                    css { +OrderItemStyles.bookName }
                    +prop.name
                }
                styledP {
                    css { +OrderItemStyles.author }
                    +prop.author
                }
            }
            styledDiv {
                css {
                    +OrderItemStyles.inline
                    +OrderItemStyles.price
                }
                val priceTotal = (prop.num * prop.price * 100).roundToInt().toDouble() / 100
                styledSpan { +"${prop.num}本" }
                styledSpan {
                    css {
                        color = Color.red
                        marginLeft = 20.px
                    }
                    +"￥${prop.price}"
                }
                styledSpan {
                    css {
                        color = Color.red
                        marginLeft = 24.px
                    }
                    +"总 ￥${priceTotal}"
                }
            }
        }
    }
}
