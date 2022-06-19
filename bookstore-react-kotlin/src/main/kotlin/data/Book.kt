package data

import kotlinx.serialization.Serializable
import react.Props
import react.State


@Serializable
data class Book(
    val id: Int, var isbn: String, var name: String, val type: String, var author: String,
    val price: Double, val description: String, var inventory: Int, var img_path: String
) {
    constructor(props: BookProps) :
            this(
                props.id, props.isbn, props.name, props.type, props.author,
                props.price, props.description, props.inventory, props.imgPath
            )

    constructor(state: BookEditState) :
            this(
                state.id, state.isbn, state.name, state.type, state.author,
                state.price.toDouble(), state.description, state.inventory.toInt(), state.img_path
            )

    constructor() : this(-1, "", "", "", "", .0, "", 0, "")
}

data class BookEditState(
    val id: Int, var isbn: String, var name: String, var type: String, var author: String,
    var price: String, var description: String, var inventory: String, var img_path: String
) : State {
    constructor() : this(-1, "", "", "", "", "0.0", "", "0", "")

    constructor(book: Book) :
            this(
                book.id, book.isbn, book.name, book.type, book.author,
                book.price.toString(), book.description, book.inventory.toString(), book.img_path
            )
}

external interface BookProps : Props {
    var id: Int
    var isbn: String
    var name: String
    var type: String
    var author: String
    var price: Double
    var description: String
    var inventory: Int
    var imgPath: String
}
