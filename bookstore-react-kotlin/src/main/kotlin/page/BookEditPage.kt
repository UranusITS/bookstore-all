package page

import antd.layout.content
import antd.layout.layout
import component.*
import data.Book
import data.BookProps
import data.getBookById
import kotlinext.js.js
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import react.dom.div
import react.fc
import react.router.useParams
import react.useState
import style.BookDetailStyles
import styled.css
import styled.styledDiv

val bookEditPage = fc<BookProps> { props ->
    val params = useParams()
    val bookId = params["bookId"]?.toInt()
    div {
        layout {
            attrs.style = js { minHeight = "100vh" }
            child(HeaderComponent::class) { }
            content {
                styledDiv {
                    css { +BookDetailStyles.frame }
                    if (bookId == null) {
                        errorComponent {
                            attrs {
                                errorCode = 404
                                extraInfo = "请给出bookId"
                            }
                        }
                    } else {
                        var book: Book? by useState(null)
                        GlobalScope.launch {
                            book = getBookById(bookId)
                        }
                        if (book != null) {
                            localStorage.setItem("editBookId", bookId.toString())
                            child(BookEditDetailComponent::class) { }
                        }
                        else {
                            errorComponent {
                                attrs {
                                    errorCode = 404
                                    extraInfo = "未找到图书"
                                }
                            }
                        }
                    }
                }
            }
            FooterComponent { }
        }
    }
}
