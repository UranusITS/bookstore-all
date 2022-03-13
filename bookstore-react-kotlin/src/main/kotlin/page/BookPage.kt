package page

import antd.layout.*
import component.BookDetail
import component.Footer
import component.Header
import kotlinext.js.js
import react.dom.div
import react.fc
import data.BookProps
import react.router.useParams

val bookPage = fc<BookProps> { props ->
    val params = useParams()
    val bookId = params["bookId"]?.toInt()
    div {
        layout {
            attrs.style = js { minHeight = "100vh" }
            child(Header::class) {
                attrs {
                    id = 100
                    name = "Uranus"
                }
            }
            content {
                attrs.style = js {
                    width = 1080
                    margin = "0 auto"
                }
                child(BookDetail::class) {
                    if(bookId == null) { }
                    else {
                        val book = defaultBookList.getById(bookId)
                        if (book != null) {
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
                        } else {
                            console.log("Book is null for id $bookId")
                        }
                    }
                }
            }
            Footer { }
        }
    }
}
