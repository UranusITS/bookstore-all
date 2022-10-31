import kotlinext.js.require
import kotlinx.browser.document
import kotlinx.browser.window
import react.Fragment
import react.create
import react.dom.client.createRoot

fun main() {
    require("antd/dist/antd.css")
    window.onload = {
        val container = document.getElementById("root") ?: error("Couldn't find root container!")
        createRoot(container).render(Fragment.create {
            basicRoute()
        })
    }
}
