package component

import antd.button.button
import antd.descriptions.descriptions
import antd.descriptions.descriptionsItem
import antd.grid.col
import antd.grid.row
import antd.icon.*
import antd.message.message
import data.*
import kotlinext.js.js
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import react.*
import react.router.dom.Link
import style.BookDetailStyles
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledSpan
import kotlin.math.roundToInt


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
                    styledSpan {
                        css { +BookDetailStyles.bookName }
                        +props.name
                    }
                    val user = getLocalUser()
                    if ((user != null) && (user.auth_level != null) && (user.auth_level.toInt() > 0)) {
                        styledSpan {
                            button {
                                Link {
                                    attrs.to = "/book-edit/${props.id}"
                                    editOutlined { }
                                }
                            }
                        }
                        styledSpan {
                            button {
                                attrs.danger = true
                                attrs.type = "primary"
                                Link {
                                    attrs.to = "/"
                                    deleteOutlined { }
                                }
                                attrs.onClick = {
                                    GlobalScope.launch {
                                        deleteBookById(props.id)
                                    }
                                }
                            }
                        }
                    }
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
                        styledSpan { +props.booktype }
                    }
                    descriptionsItem {
                        attrs.label = "定价"
                        styledSpan {
                            css {
                                color = Color.red
                                verticalAlign = VerticalAlign.bottom
                            }
                            +"￥${(props.price * 100).roundToInt().toDouble() / 100}"
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
                                    val user = getLocalUser()
                                    if (user == null) {
                                        message.error("请先登录")
                                    } else {
                                        GlobalScope.launch {
                                            val flag = addCartItem(user, Book(props))
                                            if (flag) {
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
