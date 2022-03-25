package page

import antd.layout.content
import antd.layout.layout
import component.*
import style.BookDetailStyles
import data.BookProps
import kotlinext.js.js
import react.dom.div
import react.fc
import react.router.useParams
import styled.css
import styled.styledDiv

val bookPage = fc<BookProps> { props ->
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
                        val book = defaultBookList.getById(bookId)
                        if (book != null) {
                            BookDetailComponent {
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
