package data

import react.Props
import react.State


class BookList(_books: MutableList<Book>) {
    var books: MutableList<Book> = _books
    fun getType(): List<String> {
        val re = mutableListOf<String>()
        for (book in books)
            if (re.indexOf(book.type) == -1)
                re.add(book.type)
        return re.toList()
    }
    fun getTypeWithTrue(): MutableList<Pair<String, Boolean>> {
        val re = mutableListOf<Pair<String, Boolean>>()
        for (book in books)
            if (re.indexOf(Pair(book.type, true)) == -1)
                re.add(Pair(book.type, true))
        return re
    }
    fun getById(id: Int): Book? {
        for (book in books)
            if (book.id == id)
                return book
        return null
    }
    fun getByName(name: String): Book? {
        for (book in books)
            if (book.name == name)
                return book
        return null
    }
    fun getByContent(str: String): BookList {
        val re = mutableListOf<Book>()
        for (book in books)
            if (book.type.indexOf(str) != -1 || book.name.indexOf(str) != -1 || book.author.indexOf(str) != -1 || book.description.indexOf(str) != -1)
                re.add(book)
        return BookList(re)
    }
    fun addBook(book: Book) {
        if (getById(book.id) != null)
            books.add(book)
    }
}

external interface BookListProps : Props {
    var bookList: BookList
    var sales: List<String>
}

data class BookListState(var bookList: BookList, var types: List<Pair<String, Boolean>>) : State {
}
