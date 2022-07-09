import kotlinext.js.require
import kotlinx.browser.document
import kotlinx.browser.localStorage
import kotlinx.browser.window
import react.dom.render

fun main() {
    require("antd/dist/antd.css")
    //localStorage.clear()
    window.onload = {
        document.getElementById("root")?.let { it1 ->
            render(it1) {
                basicRoute()
            }
        }
    }
}
