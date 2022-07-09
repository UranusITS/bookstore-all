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
import data.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import react.*


val defaultSales = listOf(
    "assets/carousel/book1.jpg", "assets/carousel/book2.jpg",
    "assets/carousel/book3.jpg", "assets/carousel/book4.jpg"
)

class BookListComponent(props: Props) : RComponent<Props, BookListState>(props) {
    init {
        state = BookListState(listOf(), listOf(), defaultSales)
    }

    override fun componentDidMount() {
        GlobalScope.launch {
            val books = fetchBookList()
            val typeStringList = fetchTypeList()
            val typeList = mutableListOf<Pair<String, Boolean>>()
            for (typeString in typeStringList) {
                typeList.add(Pair(typeString, false))
            }
            setState {
                types = typeList
                bookList = books
            }
        }
    }

    private var typeCount = 0

    private val handleClick: MenuClickEventHandler = { info ->
        val newList = state.types.toMutableList()
        if (state.types.indexOf(Pair(info.key, true)) != -1) {
            newList[newList.indexOf(Pair(info.key, true))] = Pair(info.key.toString(), false)
            ++typeCount
        } else {
            newList[newList.indexOf(Pair(info.key, false))] = Pair(info.key.toString(), true)
            --typeCount
        }
        GlobalScope.launch {
            var flag = true
            val types = mutableListOf<String>()
            for (type in state.types) {
                if (type.second) {
                    types.add(type.first)
                    flag = false
                }
            }
            if (flag) {
                for (type in state.types) {
                    types.add(type.first)
                }
            }
            val books = fetchBookListByTypes(types)
            setState { bookList = books }
        }
        setState { types = newList }
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
                        if (value == "") {
                            GlobalScope.launch {
                                val books = fetchBookList()
                                setState { bookList = books }
                            }
                        } else {
                            GlobalScope.launch {
                                val books = fetchBookListByText(value)
                                setState { bookList = books }
                            }
                        }
                    }
                }
            }
            SaleCarousel { attrs.sales = state.sales }
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
                            val types = state.types
                            for (pos in types.indices) {
                                menuItem {
                                    attrs.key = types[pos].first
                                    +types[pos].first
                                }
                            }
                        }
                    }
                }
                content {
                    row {
                        attrs.gutter = 24
                        for (book in state.bookList) {
                            col {
                                attrs.span = 6
                                BookItemComponent {
                                    attrs {
                                        id = book.id!!
                                        isbn = book.isbn!!
                                        name = book.name!!
                                        booktype = book.type!!
                                        author = book.author!!
                                        price = book.price!!
                                        description = book.description!!
                                        inventory = book.inventory!!
                                        imgPath = book.img_path!!
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
