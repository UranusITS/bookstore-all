package component

import antd.button.button
import antd.card.card
import antd.grid.col
import antd.grid.row
import antd.icon.payCircleOutlined
import antd.layout.content
import data.Book
import data.CartItem
import data.CartState
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import react.Props
import react.RBuilder
import react.RComponent
import react.setState
import style.SettlementItemStyles
import styled.css
import styled.styledDiv
import styled.styledP
import kotlin.math.roundToInt


class CartComponent(props: Props) : RComponent<Props, CartState>(props) {
    init {
        state = CartState(listOf(), .0)
    }

    private suspend fun fetchCartItems() {
        val userID = localStorage.getItem("id")
        val response = window.fetch("http://localhost:8080/cart-item/items?user-id=$userID")
            .await()
            .text()
            .await()
        val cartItemList = Json.decodeFromString<List<CartItem>>(response)
        var priceTotal = .0
        for (cartItem in cartItemList) {
            if (cartItem.checked) {
                val bookResponse = window.fetch("http://localhost:8080/book/get-book-by-id?id=${cartItem.book_id}")
                    .await()
                    .text()
                    .await()
                val bookPrice = Json.decodeFromString<Book>(bookResponse).price
                priceTotal += cartItem.num * bookPrice
            }
        }
        setState(CartState(cartItemList, priceTotal))
    }

    override fun componentDidMount() {
        GlobalScope.launch {
            console.log("OK1")
            fetchCartItems()
            console.log("OK2")
        }
    }

    override fun RBuilder.render() {
        content {
            attrs.style = kotlinext.js.js {
                width = 1080.px
                margin = "120px auto"
            }
            row {
                attrs.gutter = 24
                console.log(state.cartItemList)
                for (item in state.cartItemList) {
                    col {
                        attrs.span = 24
                        CartItemComponent {
                            attrs {
                                id = item.id
                                userId = item.user_id
                                bookId = item.book_id
                                num = item.num
                                checked = item.checked
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
                                +"￥${state.priceTotal}"
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
