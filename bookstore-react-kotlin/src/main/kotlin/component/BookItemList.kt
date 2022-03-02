package component

import data.Book
import react.*
import antd.grid.col
import antd.grid.row


external interface BookListProps : Props {
    var books: List<Book>
}

data class BookListState(val books: List<Book>) : State

class BookList (props: BookListProps) : RComponent<BookListProps, BookListState>(props) {
    init {
        state = BookListState(props.books)
    }

    override fun RBuilder.render() {
        row {
            attrs.gutter = 24
            for (book in state.books) {
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
