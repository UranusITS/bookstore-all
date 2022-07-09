package component

import antd.ChangeEventHandler
import antd.button.button
import antd.grid.col
import antd.grid.row
import antd.icon.*
import antd.input.input
import antd.input.textArea
import antd.message.message
import antd.space.space
import data.*
import kotlinext.js.js
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.*
import react.router.dom.Link
import style.BookDetailStyles
import styled.css
import styled.styledDiv
import styled.styledSpan


class BookEditDetailComponent(props: Props) : RComponent<Props, BookState>(props) {
    init {
        state = BookState()
    }

    override fun componentDidMount() {
        GlobalScope.launch {
            val bookId = localStorage.getItem("editBookId")
            if (bookId != null) {
                val book = getBookById(bookId.toInt())
                if (book != null) {
                    setState(BookState(book))
                }
            }
        }
    }

    private val nameInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState { name = it.target.asDynamic().value as String }
    }

    private val typeInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState { type = it.target.asDynamic().value as String }
    }

    private val authorInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState { author = it.target.asDynamic().value as String }
    }

    private val imgPathInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState { img_path = it.target.asDynamic().value as String }
    }

    private val isbnInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState { isbn = it.target.asDynamic().value as String }
    }

    private val inventoryInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState { inventory = it.target.asDynamic().value as String }
    }

    private val priceInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState { price = it.target.asDynamic().value as String }
    }

    private val descriptionInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState { description = it.target.asDynamic().value as String }
    }

    override fun RBuilder.render() {
        //console.log(state)
        space {
            attrs.direction = "vertical"
            attrs.size = 20
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"编号"
                }
                styledSpan {
                    input {
                        attrs.style = js { width = 480 }
                        attrs.disabled = true
                        attrs.value = state.id
                        attrs.onChange = nameInputChangeHandler
                    }
                }
            }
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"书名"
                }
                styledSpan {
                    input {
                        attrs.style = js { width = 480 }
                        attrs.placeholder = "请填入书名"
                        attrs.value = state.name
                        attrs.onChange = nameInputChangeHandler
                    }
                }
            }
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"分类"
                }
                styledSpan {
                    input {
                        attrs.style = js { width = 480 }
                        attrs.placeholder = "请填入分类"
                        attrs.value = state.type
                        attrs.onChange = typeInputChangeHandler
                    }
                }
            }
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"作者"
                }
                styledSpan {
                    input {
                        attrs.style = js { width = 480 }
                        attrs.placeholder = "请填入作者名"
                        attrs.value = state.author
                        attrs.onChange = authorInputChangeHandler
                    }
                }
            }
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"封面"
                }
                styledSpan {
                    input {
                        attrs.style = js { width = 480 }
                        attrs.placeholder = "请填入书籍图片地址"
                        attrs.value = state.img_path
                        attrs.onChange = imgPathInputChangeHandler
                    }
                }
            }
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"ISBN"
                }
                styledSpan {
                    input {
                        attrs.style = js { width = 480 }
                        attrs.placeholder = "请填入ISBN信息"
                        attrs.value = state.isbn
                        attrs.onChange = isbnInputChangeHandler
                    }
                }
            }
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"库存"
                }
                styledSpan {
                    input {
                        attrs.style = js { width = 480 }
                        attrs.placeholder = "请填入库存余量"
                        attrs.value = state.inventory
                        attrs.onChange = inventoryInputChangeHandler
                    }
                }
            }
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"价格"
                }
                styledSpan {
                    input {
                        attrs.style = js { width = 480 }
                        attrs.placeholder = "请填入价格"
                        attrs.value = state.price
                        attrs.onChange = priceInputChangeHandler
                    }
                }
            }
            styledDiv {
                styledSpan {
                    css { marginRight = 64.px }
                    +"简介"
                }
                styledSpan {
                    textArea {
                        attrs.style = js { width = 480 }
                        attrs.rows = 6
                        attrs.placeholder = "请填入简介"
                        attrs.value = state.description
                        attrs.onChange = descriptionInputChangeHandler
                    }
                }
            }
            styledDiv {
                css { +BookDetailStyles.buttons }
                row {
                    col {
                        attrs.span = "12"
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
                                        GlobalScope.launch {
                                            updateBook(Book(state))
                                            message.success("保存成功")
                                        }
                                    }
                                }
                                saveOutlined { }
                                +"保存修改"
                            }
                        }
                    }
                    col {
                        attrs.span = "12"
                        styledDiv {
                            css { +BookDetailStyles.button }
                            button {
                                attrs.style = js {
                                    width = 160.px
                                    margin = "0 auto"
                                }
                                attrs.size = "large"
                                Link {
                                    attrs.to = "/book/${state.id}"
                                    closeCircleOutlined { }
                                    +"退出修改"
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
