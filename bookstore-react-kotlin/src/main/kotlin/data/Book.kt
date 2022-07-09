package data

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.Props
import react.State
import kotlin.js.Promise


@Serializable
data class Book(
    val id: Int? = null,
    var isbn: String? = null,
    var name: String? = null,
    val type: String? = null,
    var author: String? = null,
    val price: Double? = null,
    val description: String? = null,
    var inventory: Int? = null,
    var img_path: String? = null
) {
    constructor(props: BookProps) :
            this(
                props.id, props.isbn, props.name, props.booktype, props.author,
                props.price, props.description, props.inventory, props.imgPath
            )

    constructor(state: BookState) :
            this(
                state.id, state.isbn, state.name, state.type, state.author,
                state.price?.toDouble(), state.description, state.inventory?.toInt(), state.img_path
            )
}

data class BookState(
    val id: Int? = null,
    var isbn: String? = null,
    var name: String? = null,
    var type: String? = null,
    var author: String? = null,
    var price: String? = null,
    var description: String? = null,
    var inventory: String? = null,
    var img_path: String? = null
) : State {
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
    var booktype: String
    var author: String
    var price: Double
    var description: String
    var inventory: Int
    var imgPath: String
}

suspend fun getBookById(id: Int): Book? {
    val response = window.fetch("$backendUrl/book/get-book-by-id?id=$id")
        .await()
        .text()
        .await()
    console.log(response)
    return Json.decodeFromString(response)
}

suspend fun updateBook(book: Book) {
    val headers = Headers()
    headers.append("Content-Type", "application/json;charset=UTF-8")
    window.fetch(
        "$backendUrl/book/update-book",
        RequestInit(
            method = "POST",
            headers = headers,
            body = Json.encodeToString(book)
        )
    )
        .await()
        .text()
        .await()
}

suspend fun deleteBookById(id: Int) {
    window.fetch("$backendUrl/book/delete-book?id=$id")
        .await()
        .text()
        .await()
}
