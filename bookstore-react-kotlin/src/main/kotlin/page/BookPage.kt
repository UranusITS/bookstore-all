package page

import antd.layout.content
import antd.layout.layout
import component.BookDetailComponent
import component.FooterComponent
import component.HeaderComponent
import component.errorComponent
import data.Book
import data.BookProps
import data.getBookById
import kotlinext.js.js
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.js.get
import react.dom.div
import react.fc
import react.router.useParams
import react.useState
import style.BookDetailStyles
import styled.css
import styled.styledDiv

val bookPage = fc<BookProps> { _ ->
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
                            BookDetailComponent {
                                attrs {
                                    id = book!!.id!!
                                    isbn = book!!.isbn!!
                                    name = book!!.name!!
                                    booktype = book!!.type!!
                                    author = book!!.author!!
                                    price = book!!.price!!
                                    description = book!!.description!!
                                    inventory = book!!.inventory!!
                                    imgPath = book!!.img_path!!
                                }
                            }
                        } else {
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
