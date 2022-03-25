package component

import antd.grid.col
import antd.grid.row
import antd.input.search
import antd.layout.content
import antd.layout.layout
import antd.layout.sider
import antd.menu.MenuClickEventHandler
import antd.menu.menu
import antd.menu.menuItem
import antd.menu.subMenu
import data.BookListProps
import data.BookListState
import react.RBuilder
import react.RComponent
import react.key

class BookListComponent (props: BookListProps) : RComponent<BookListProps, BookListState>(props) {
    init {
        state = BookListState(props.bookList, props.bookList.getTypeWithTrue())
    }

    private var typeCount = 0

    private val handleClick: MenuClickEventHandler = { info ->
        val newList = state.types.toMutableList()
        if (state.types.indexOf(Pair(info.key, true)) != -1) {
            newList[newList.indexOf(Pair(info.key, true))] = Pair(info.key.toString(), false)
            ++typeCount
        }
        else {
            newList[newList.indexOf(Pair(info.key, false))] = Pair(info.key.toString(), true)
            --typeCount
        }
        setState(BookListState(state.bookList, newList))
    }

    override fun RBuilder.render() {
        content {
            attrs.style = kotlinext.js.js {
                width = 1280
                margin = "72px auto"
            }
            search {
                attrs {
                    style = kotlinext.js.js { marginTop = 24 }
                    placeholder = "输入书名，作者名，......"
                    enterButton = true
                    size = "large"
                    onSearch = { value, _ ->
                        if (value == "")
                            setState(BookListState(props.bookList, state.types))
                        else
                            setState(BookListState(props.bookList.getByContent(value), state.types))
                    }
                }
            }
            SaleCarousel { attrs.sales = props.sales }
            layout {
                sider {
                    attrs.style = kotlinext.js.js {
                        background = "rgb(240, 242, 245)"
                    }
                    menu {
                        attrs {
                            mode = "inline"
                            multiple = true
                            defaultOpenKeys = arrayOf("type")
                            onClick = handleClick
                        }
                        subMenu {
                            attrs.title = "图书种类"
                            attrs.key = "type"
                            val types = props.bookList.getType()
                            for (pos in types.indices) {
                                menuItem {
                                    attrs.key = types[pos]
                                    +types[pos]
                                }
                            }
                        }
                    }
                }
                content {
                    row {
                        attrs.gutter = 24
                        for (book in state.bookList.books) {
                            if (typeCount!=0 && state.types.indexOf(Pair(book.type, false)) == -1)
                                continue
                            col {
                                attrs.span = 6
                                BookItemComponent {
                                    attrs {
                                        id = book.id
                                        isbn = book.isbn
                                        name = book.name
                                        type = book.type
                                        author = book.author
                                        price = book.price
                                        description = book.description
                                        inventory = book.inventory
                                        imgPath = book.imgPath
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
