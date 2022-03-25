package component

import antd.button.button
import antd.card.card
import antd.grid.col
import antd.grid.row
import antd.icon.payCircleOutlined
import antd.icon.shoppingCartOutlined
import antd.layout.content
import data.Book
import data.SettlementState
import kotlinx.browser.localStorage
import kotlinx.css.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import react.Props
import react.RBuilder
import react.RComponent
import style.SettlementItemStyles
import styled.css
import styled.styledDiv
import styled.styledP
import kotlin.math.roundToInt


class SettlementComponent(props: Props) : RComponent<Props, SettlementState>(props) {
    init {
        val storageSettlement = localStorage.getItem("settlement")
        state = if (storageSettlement != null) {
            val books = Json.decodeFromString<SettlementState>(storageSettlement)
            console.log(books)
            books
        } else SettlementState(listOf())
    }

    override fun componentDidMount() {
        val storageSettlement = localStorage.getItem("settlement")
        if (storageSettlement != null) {
            val books = Json.decodeFromString<SettlementState>(storageSettlement)
            console.log(books)
            setState(books)
        }
    }

    override fun RBuilder.render() {
        var priceSum = 0.0
        content {
            attrs.style = kotlinext.js.js {
                width = 1080.px
                margin = "120px auto"
            }
            row {
                attrs.gutter = 24
                for (item in state.books) {
                    priceSum += item.first.price * item.second
                    col {
                        attrs.span = 24
                        SettlementItemComponent {
                            attrs {
                                book = item.first
                                num = item.second
                            }
                        }
                    }
                }
            }
            row {
                attrs.style = kotlinext.js.js { marginTop = 64 }
                attrs.gutter = 24
                col {
                    card {
                        attrs.bordered = true
                        attrs.style = kotlinext.js.js {
                            margin = "0 auto"
                            width = 1200
                        }
                        styledDiv {
                            css {
                                +SettlementItemStyles.inline
                                +SettlementItemStyles.bottomMargin
                            }
                            styledP {
                                css { +SettlementItemStyles.bottomMargin }
                                +"总价："
                            }
                        }
                        styledDiv {
                            css {
                                +SettlementItemStyles.inline
                                +SettlementItemStyles.bottomMargin
                                width = 500.px
                            }
                            styledP {
                                css {
                                    +SettlementItemStyles.bottomMargin
                                    color = Color.red
                                    fontSize = 18.pt
                                }
                                priceSum *= 100
                                priceSum = priceSum.roundToInt().toDouble() / 100
                                +"￥${priceSum}"
                            }
                        }
                        styledDiv {
                            css {
                                +SettlementItemStyles.inline
                                +SettlementItemStyles.bottomMargin
                            }
                            styledDiv {
                                css {
                                    width = 560.px
                                    textAlign = TextAlign.right
                                }
                                button {
                                    attrs {
                                        style = kotlinext.js.js {
                                            width = 160.px
                                            margin = "0 auto"
                                        }
                                        type = "primary"
                                        size = "large"
                                    }
                                    payCircleOutlined { }
                                    +"立即购买"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
