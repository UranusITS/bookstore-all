package page

import antd.input.search
import component.BookList
import antd.layout.*
import component.Header
import component.SaleCarousel
import kotlinext.js.js
import react.Props
import react.dom.a
import react.dom.div
import react.dom.p
import react.fc
import data.BookProps
import data.BookState
import react.useState

val bookPage = fc<BookProps> { props ->
    div {
        layout {
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
                p {
                    +"//UNDER CONSTRUCTION//"
                }
            }
            footer {
                attrs.style = kotlinext.js.js{
                    textAlign = "center"
                    background = "white"
                }
                p {
                    +"Designed by "
                    a {
                        attrs.href = "https://github.com/UranusITS"
                        +"Uranus"
                    }
                }
                p {
                    +"With "
                    a {
                        attrs.href = "https://kotlinlang.org/"
                        +"Kotlin"
                    }
                    +" & "
                    a {
                        attrs.href = "https://ant.design/"
                        +"Ant Design"
                    }
                    +" & ❤"
                }
            }
        }
    }
}
