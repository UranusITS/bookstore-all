package component

import antd.button.button
import antd.descriptions.descriptions
import antd.descriptions.descriptionsItem
import antd.grid.col
import antd.grid.row
import antd.icon.payCircleOutlined
import antd.icon.shoppingCartOutlined
import antd.icon.starOutlined
import antd.message.message
import style.BookDetailStyles
import data.Book
import data.BookProps
import data.SettlementState
import kotlinext.js.js
import kotlinx.browser.localStorage
import kotlinx.css.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import react.fc
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledSpan
import kotlinx.serialization.json.Json


val BookDetailComponent = fc<BookProps> { props ->
    styledDiv {
        row {
            col {
                attrs.span = "12"
                styledDiv {
                    styledImg {
                        css { width = 350.px }
                        attrs.src = props.imgPath
                    }
                }
            }
            col {
                attrs.span = "12"
                styledDiv {
                    css { +BookDetailStyles.bookName }
                    +props.name
                }
                descriptions {
                    attrs.column = 1
                    descriptionsItem {
                        attrs.label = "作者"
                        styledSpan {
                            +props.author
                        }
                    }
                    descriptionsItem {
                        attrs.label = "分类"
                        styledSpan { +props.type }
                    }
                    descriptionsItem {
                        attrs.label = "定价"
                        styledSpan {
                            css {
                                color = Color.red
                                verticalAlign = VerticalAlign.bottom
                            }
                            +"￥${props.price}"
                        }
                    }
                    descriptionsItem {
                        attrs.label = "库存"
                        styledSpan {
                            if (props.inventory == 0) {
                                +"暂无库存"
                            } else {
                                +"剩余${props.inventory}本"
                            }
                        }
                    }
                    descriptionsItem {
                        attrs.label = "作品简介"
                        styledSpan { +props.description }
                    }
                }
            }
        }
        styledDiv {
            css { +BookDetailStyles.buttons }
            row {
                col {
                    attrs.span = "8"
                    styledDiv {
                        css { +BookDetailStyles.button }
                        button {
                            attrs {
                                style = js {
                                    width = 160.px
                                    margin = "0 auto"
                                }
                                type = "primary"
                                size = "large"
                                onClick = {
                                    val storageSettlement = localStorage.getItem("settlement")
                                    console.log(storageSettlement)
                                    if (storageSettlement != null) {
                                        val books = Json.decodeFromString<SettlementState>(storageSettlement)
                                        val newBooks = books.books.toMutableList()
                                        var foundFlag = false
                                        for (book in newBooks)
                                            if (book.first.id == props.id) {
                                                foundFlag = true
                                                break
                                            }
                                        if (!foundFlag) {
                                            val book = Book(props.id, props.isbn, props.name, props.type, props.author,
                                                props.price, props.description, props.inventory, props.imgPath)
                                            newBooks.add(Pair(book, 1))
                                            localStorage.setItem("settlement", Json.encodeToString(SettlementState(newBooks)))
                                            message.success("加入购物车成功")
                                        }
                                        else {
                                            message.info("已在购物车中")
                                        }
                                    }
                                    else {
                                        val book = Book(props.id, props.isbn, props.name, props.type, props.author,
                                            props.price, props.description, props.inventory, props.imgPath)
                                        localStorage.setItem("settlement", Json.encodeToString(SettlementState(listOf(Pair(book, 1)))))
                                        message.success("加入购物车成功")
                                    }
                                }
                            }
                            shoppingCartOutlined { }
                            +"加入购物车"
                        }
                    }
                }
                col {
                    attrs.span = "8"
                    styledDiv {
                        css { +BookDetailStyles.button }
                        button {
                            attrs.style = js {
                                width = 160.px
                                margin = "0 auto"
                            }
                            attrs.size = "large"
                            payCircleOutlined { }
                            +"立即购买"
                        }
                    }
                }
                col {
                    attrs.span = "8"
                    styledDiv {
                        css { +BookDetailStyles.button }
                        button {
                            attrs.style = js {
                                width = 160.px
                                margin = "0 auto"
                            }
                            attrs.size = "large"
                            starOutlined { }
                            +"加入收藏"
                        }
                    }
                }
            }
        }
    }
}
