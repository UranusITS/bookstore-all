import kotlinext.js.require
import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window

fun main() {
    require("antd/dist/antd.css")
    window.onload = {
        document.getElementById("root")?.let { it1 ->
            render(it1) {
                basicRoute()
            }
        }
    }
}
