package data

import react.State


data class BookListState(
    var bookList: List<Book>,
    var types: List<Pair<String, Boolean>>,
    var sales: List<String>
) : State
