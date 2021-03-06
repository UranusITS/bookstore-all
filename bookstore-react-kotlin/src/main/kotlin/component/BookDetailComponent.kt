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
                        attrs.label = "??????"
                        styledSpan {
                            +props.author
                        }
                    }
                    descriptionsItem {
                        attrs.label = "??????"
                        styledSpan { +props.booktype }
                    }
                    descriptionsItem {
                        attrs.label = "??????"
                        styledSpan {
                            css {
                                color = Color.red
                                verticalAlign = VerticalAlign.bottom
                            }
                            +"???${props.price}"
                        }
                    }
                    descriptionsItem {
                        attrs.label = "??????"
                        styledSpan {
                            if (props.inventory == 0) {
                                +"????????????"
                            } else {
                                +"??????${props.inventory}???"
                            }
                        }
                    }
                    descriptionsItem {
                        attrs.label = "????????????"
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
                                        message.error("????????????")
                                    } else {
                                        GlobalScope.launch {
                                            val flag = addCartItem(user, Book(props))
                                            if (flag) {
                                                message.success("?????????????????????")
                                            } else {
                                                message.info("??????????????????")
                                            }
                                        }
                                    }
                                }
                            }
                            shoppingCartOutlined { }
                            +"???????????????"
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
                            +"????????????"
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
                            +"????????????"
                        }
                    }
                }
            }
        }
    }
}
