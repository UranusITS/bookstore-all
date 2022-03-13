package component

import antd.button.button
import antd.descriptions.descriptions
import antd.descriptions.descriptionsItem
import antd.grid.row
import antd.grid.col
import antd.icon.payCircleOutlined
import antd.icon.shoppingCartOutlined
import antd.icon.starOutlined
import react.*
import react.RBuilder
import data.*
import kotlinext.js.js
import kotlinx.css.*
import styled.css
import styled.styledDiv
import styled.styledImg
import styled.styledSpan


class BookDetail (props: BookProps) : RComponent<BookProps, BookState>(props) {
    override fun RBuilder.render() {
        styledDiv {
            css { +BookDetailStyles.frame }
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
                                }
                                else {
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
                                attrs.style = js {
                                    width = 160.px
                                    margin = "0 auto"
                                }
                                attrs.type = "primary"
                                attrs.size = "large"
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
}

