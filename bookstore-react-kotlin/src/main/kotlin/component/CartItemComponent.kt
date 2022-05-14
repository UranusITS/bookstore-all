package component

import antd.button.button
import antd.card.card
import antd.icon.deleteOutlined
import antd.inputnumber.inputNumber
import data.Book
import data.CartItemProps
import kotlinext.js.js
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import react.buildElement
import react.fc
import react.useEffectOnce
import react.useState
import style.SettlementItemStyles
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledP


suspend fun fetchBook(bookId: Int): Book {
    val response = window.fetch("http://localhost:8080/book/get-book-by-id?id=$bookId")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

val CartItemComponent = fc<CartItemProps> { props ->
    var book: Book by useState(Book())
    GlobalScope.launch {
        book = fetchBook(props.bookId)
    }
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
                    attrs.src = book.img_path
                }
            }
            styledDiv {
                css {
                    +SettlementItemStyles.inline
                    +SettlementItemStyles.textInfo
                }
                styledP {
                    css { +SettlementItemStyles.bookName }
                    +book.name
                }
                styledP {
                    css { +SettlementItemStyles.author }
                    +book.author
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
                        attrs.max = book.inventory
                        attrs.defaultValue = props.num
                        attrs.onChange = {
                            val num = attrs.value
                            console.log(num)
                            /**
                            GlobalScope.launch {
                                window.fetch("http://localhost:8080/cart-item/update-num?id=${props.id}&num=$num")
                            }
                            */
                        }
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
                styledP { +"￥${props.num * book.price}" }
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
