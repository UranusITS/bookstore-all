import kotlinext.js.require
import kotlinx.browser.document
import kotlinx.browser.window
import react.dom.render

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
