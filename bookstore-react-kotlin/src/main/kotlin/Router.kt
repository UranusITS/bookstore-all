import kotlinx.browser.localStorage
import page.bookPage
import page.homePage
import page.settlementPage
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
                    attrs.element = createElement(homePage)
                }
                Route {
                    attrs.path = "settlement"
                    attrs.element = createElement(settlementPage)
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
