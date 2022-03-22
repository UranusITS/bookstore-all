package component

import data.Book
import react.*
import antd.grid.col
import antd.grid.row
import antd.input.search
import antd.layout.content
import kotlinext.js.js
import page.defaultSales


external interface BookListProps : Props {
    var bookList: BookList
}

data class BookListState(var bookList: BookList) : State {
}

class BookList(_books: MutableList<Book>) {
    var books: MutableList<Book> = _books
    fun getById(id: Int): Book? {
        for (book in books) {
            if (book.id == id)
                return book
        }
        return null
    }
    fun getByName(name: String): Book? {
        for (book in books) {
            if (book.name == name)
                return book
        }
        return null
    }
    fun getByContent(str: String): BookList {
        val re = mutableListOf<Book>()
        for (book in books) {
            if (book.type.indexOf(str) != -1 || book.name.indexOf(str) != -1 || book.author.indexOf(str) != -1 || book.description.indexOf(str) != -1) {
                re.add(book)
            }
        }
        console.log(re)
        return BookList(re)
    }
    fun addBook(book: Book) {
        if (getById(book.id) != null) {
            books.add(book)
        }
    }
}

class BookListComponent (props: BookListProps) : RComponent<BookListProps, BookListState>(props) {
    init {
        state = BookListState(props.bookList)
    }

    override fun RBuilder.render() {
        content {
            attrs.style = js {
                width = 1080
                margin = "0 auto"
            }
            search {
                attrs {
                    style = js { marginTop = 24 }
                    placeholder = "输入书名，作者名，......"
                    enterButton = true
                    size = "large"
                    onSearch = { value,_ ->
                        if (value == "")
                            setState(BookListState(props.bookList))
                        else
                            setState(BookListState(props.bookList.getByContent(value)))
                    }
                }
            }
            SaleCarousel { attrs.sales = defaultSales }
            row {
                attrs.gutter = 24
                for (book in state.bookList.books) {
                    col {
                        attrs.span = 6
                        BookItem {
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
