package component

import antd.ChangeEventHandler
import antd.button.button
import antd.card.card
import antd.icon.deleteOutlined
import antd.inputnumber.inputNumber
import data.*
import kotlinext.js.js
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.*
import style.SettlementItemStyles
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP


class CartItemComponent(props: CartItemProps) : RComponent<CartItemProps, CartItemState>(props) {
    init {
        state = CartItemState(CartItem(props), Book())
    }

    override fun componentDidMount() {
        GlobalScope.launch {
            fetchBook(state.cartItem.book_id)
        }
    }

    private suspend fun fetchBook(bookId: Int) {
        val response = window.fetch("http://localhost:8080/book/get-book-by-id?id=$bookId")
            .await()
            .text()
            .await()
        setState { book = Json.decodeFromString(response) }
    }

    private val numInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        val num = it.asDynamic() as Int
        console.log(num)
        GlobalScope.launch {
            window.fetch("http://localhost:8080/cart-item/update-num?id=${state.cartItem.id}&num=$num")
                .then { props.onPriceChange() }
        }
        val newCartItem = state.cartItem
        newCartItem.num = num
        setState { cartItem = newCartItem }
    }

    override fun RBuilder.render() {
        styledDiv {
            card {
                attrs.bordered = true
                attrs.style = js {
                    margin = "0 auto"
                    width = 1200
                }
                styledDiv {
                    css { +SettlementItemStyles.inline }
                    styledImg {
                        attrs.width = "128px"
                        attrs.src = state.book.img_path
                    }
                }
                styledDiv {
                    css {
                        +SettlementItemStyles.inline
                        +SettlementItemStyles.textInfo
                    }
                    styledP {
                        css { +SettlementItemStyles.bookName }
                        +state.book.name
                    }
                    styledP {
                        css { +SettlementItemStyles.author }
                        +state.book.author
                    }
                }
                styledDiv {
                    css {
                        +SettlementItemStyles.inline
                        +SettlementItemStyles.numSelect
                    }
                    styledDiv {
                        css { +SettlementItemStyles.inline }
                        styledP {
                            css { +SettlementItemStyles.numInfo }
                            +"购买"
                        }
                    }
                    styledDiv {
                        css { +SettlementItemStyles.inline }
                        inputNumber {
                            attrs.size = "large"
                            attrs.min = 1
                            attrs.max = state.book.inventory
                            attrs.value = state.cartItem.num
                            attrs.onChange = numInputChangeHandler
                        }
                    }
                    styledDiv {
                        css { +SettlementItemStyles.inline }
                        styledP {
                            css { +SettlementItemStyles.numInfo }
                            +"本"
                        }
                    }
                }
                styledDiv {
                    css {
                        +SettlementItemStyles.inline
                        +SettlementItemStyles.price
                    }
                    styledP { +"￥${state.book.price}" }
                }
                styledDiv {
                    css {
                        +SettlementItemStyles.inline
                        +SettlementItemStyles.deleteIcon
                    }
                    button {
                        attrs.size = "large"
                        attrs.icon = buildElement {
                            deleteOutlined { }
                        }
                    }
                }
            }
        }
    }
}
