import component.BookItem
import history.HashHistory
import history.History
import history.createHashHistory
import kotlinext.js.js
import page.HomepageProps
import page.bookPage
import page.homepage
import react.*
import react.router.Route
import react.router.Routes
import react.router.dom.HashRouter

class BasicRoute(props: Props): RComponent<Props, State>(props) {
    override fun RBuilder.render() {
        HashRouter {
            Routes {
                Route {
                    attrs.index = true
                    attrs.element = createElement(homepage, js { id = 100 ;name = "Uranus" })
                }
                Route {
                    attrs.path = "book"
                    attrs.element = createElement(bookPage)
                    Route {
                        attrs.path = ":bookId"
                    }
                }
            }
        }
    }
}

fun RBuilder.basicRoute() = child(BasicRoute::class) { }
