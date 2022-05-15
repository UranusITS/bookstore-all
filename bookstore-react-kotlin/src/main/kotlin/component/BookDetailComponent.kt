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
import data.BookProps
import data.CartItem
import kotlinext.js.js
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.fc
import style.BookDetailStyles
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledSpan


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
                                    val userId = localStorage.getItem("id")
                                    if (userId == null || userId.toInt() == -1) {
                                        message.error("请先登录")
                                    } else {
                                        GlobalScope.launch {
                                            val headers = Headers()
                                            headers.append("Content-Type", "application/json;charset=UTF-8")
                                            val cartItem = CartItem(null, userId.toInt(), props.id, 1, true)
                                            val response = window.fetch(
                                                "http://localhost:8080/cart-item/add-item",
                                                RequestInit(
                                                    method = "POST",
                                                    headers = headers,
                                                    body = Json.encodeToString(cartItem)
                                                )
                                            )
                                                .await()
                                                .text()
                                                .await()
                                                .toBoolean()
                                            if (response) {
                                                message.success("加入购物车成功")
                                            } else {
                                                message.info("已在购物车中")
                                            }
                                        }
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
