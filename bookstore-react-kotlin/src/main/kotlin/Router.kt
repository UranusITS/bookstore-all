import page.*
import react.*
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter

val basicRoute = fc<Props> {
    HashRouter {
        Routes {
            Route {
                attrs.index = true
                attrs.element = createElement(homePage)
            }
            Route {
                attrs.path = "cart"
                attrs.element = createElement(cartPage)
            }
            Route {
                attrs.path = "order"
                attrs.element = createElement(orderPage)
            }
            Route {
                attrs.path = "admin"
                attrs.element = createElement(adminPage)
            }
            Route {
                attrs.path = "book"
                attrs.element = createElement(bookPage)
                Route {
                    attrs.path = ":bookId"
                }
            }
            Route {
                attrs.path = "book-edit"
                attrs.element = createElement(bookEditPage)
                Route {
                    attrs.path = ":bookId"
                }
            }
        }
    }
}
