package component

import react.*
import react.dom.*
import antd.card.card
import antd.card.cardMeta
import kotlinext.js.js
import react.RBuilder
import data.*
import react.router.dom.Link
import react.router.useHref
import styled.css
import styled.styledDiv
import styled.styledP


class BookItem (props: BookProps) : RComponent<BookProps, BookState>(props) {
    init {
        state = BookState(props)
    }

    override fun RBuilder.render() {
        styledDiv {
            css { +BookItemStyles.frame }
            Link {
                attrs.to = "/book"
                attrs.state = state
                card {
                    attrs {
                        hoverable = true
                        bordered = false
                        style = js {
                            width = 240
                            paddingTop = 24
                            margin = "0 auto"
                        }
                        cover = buildElement {
                            img {
                                attrs {
                                    alt = state.book.name
                                    src = state.book.imgPath
                                }
                            }
                        }
                    }
                    cardMeta {
                        attrs {
                            title = state.book.name
                            description = state.book.author
                        }
                    }
                    styledP {
                        css { +BookItemStyles.price }
                        +"￥${state.book.price}"
                    }
                    styledP {
                        css { +BookItemStyles.inventory }
                        +"库存剩余${state.book.inventory}件"
                    }
                }
            }
        }
    }
}
