import kotlinext.js.require
import browser.document
import browser.window
import react.create
import react.dom.client.createRoot

fun main()  {
    require("antd/dist/antd.css")
    window.onload = {
        val container = document.getElementById("root") ?: error("Couldn't find root container!")
        val root = createRoot(container)
        root.render(basicRoute.create())
    }
}
