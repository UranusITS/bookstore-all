package data

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.State


data class BookListState(
    var bookList: List<Book>,
    var types: List<Pair<String, Boolean>>,
    var sales: List<String>
) : State

suspend fun fetchBookList(): List<Book> {
    val response = window.fetch("$backendUrl/book/books")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

suspend fun fetchBookListByText(text: String): List<Book> {
    val response = window.fetch("$backendUrl/book/get-books-by-text?text=$text")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

suspend fun fetchBookListByTypes(types: List<String>): List<Book> {
    val headers = Headers()
    headers.append("Content-Type", "application/json;charset=UTF-8")
    val response = window.fetch(
        "$backendUrl/book/get-books-by-types",
        RequestInit(method = "POST", headers = headers, body = Json.encodeToString(types))
    )
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}

suspend fun fetchTypeList(): List<String> {
    val response = window.fetch("$backendUrl/book/types")
        .await()
        .text()
        .await()
    return Json.decodeFromString(response)
}
