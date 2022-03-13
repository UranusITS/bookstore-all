package component

import data.Book
import react.*
import antd.grid.col
import antd.grid.row


external interface BookListProps : Props {
    var bookList: BookList
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
    fun getBySearchContent(str: String): BookList {
        val re = mutableListOf<Book>()
        for (book in books) {
            if (book.toString().indexOf(str) != -1) {
                re.add(book)
            }
        }
        return BookList(re)
    }
    fun addBook(book: Book) {
        if (getById(book.id) != null) {
            books.add(book)
        }
    }
}

class BookListComponent (props: BookListProps) : RComponent<BookListProps, State>(props) {
    private val bookList = props.bookList
    override fun RBuilder.render() {
        row {
            attrs.gutter = 24
            for (book in bookList.books) {
                col {
                    attrs.span = 6
                    child(BookItem::class) {
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
