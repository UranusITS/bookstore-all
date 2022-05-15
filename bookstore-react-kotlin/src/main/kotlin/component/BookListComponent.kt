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
import data.BookListState
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.*


val defaultSales = listOf(
    "assets/carousel/book1.jpg", "assets/carousel/book2.jpg",
    "assets/carousel/book3.jpg", "assets/carousel/book4.jpg"
)

class BookListComponent(props: Props) : RComponent<Props, BookListState>(props) {
    init {
        state = BookListState(listOf(), listOf(), defaultSales)
    }

    private suspend fun fetchBookList() {
        val response = window.fetch("http://localhost:8080/book/books")
            .await()
            .text()
            .await()
        setState { bookList = Json.decodeFromString(response) }
    }

    private suspend fun fetchBookListByText(text: String) {
        val response = window.fetch("http://localhost:8080/book/get-books-by-text?text=$text")
            .await()
            .text()
            .await()
        setState { bookList = Json.decodeFromString(response) }
    }

    private suspend fun fetchBookListByTypes() {
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
        val headers = Headers()
        headers.append("Content-Type", "application/json;charset=UTF-8")
        val response = window.fetch(
            "http://localhost:8080/book/get-books-by-types",
            RequestInit(method = "POST", headers = headers, body = Json.encodeToString(types))
        )
            .await()
            .text()
            .await()
        setState { bookList = Json.decodeFromString(response) }
    }

    private suspend fun fetchTypeList() {
        val response = window.fetch("http://localhost:8080/book/types")
            .await()
            .text()
            .await()
        val typeStringList = Json.decodeFromString<List<String>>(response)
        val typeList = mutableListOf<Pair<String, Boolean>>()
        for (typeString in typeStringList) {
            typeList.add(Pair(typeString, false))
        }
        setState { types = typeList }
    }

    override fun componentDidMount() {
        GlobalScope.launch {
            fetchBookList()
            fetchTypeList()
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
            fetchBookListByTypes()
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
                                fetchBookList()
                            }
                        } else {
                            GlobalScope.launch {
                                fetchBookListByText(value)
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
                                        id = book.id
                                        isbn = book.isbn
                                        name = book.name
                                        type = book.type
                                        author = book.author
                                        price = book.price
                                        description = book.description
                                        inventory = book.inventory
                                        imgPath = book.img_path
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
