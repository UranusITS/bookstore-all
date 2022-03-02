import react.dom.render
import kotlinx.browser.document
import kotlinx.browser.window
import page.Homepage

fun main() {
    window.onload = {
        document.getElementById("root")?.let { it1 ->
            render(it1) {
                child(Homepage::class) {
                    attrs {
                        name = "UranusITS"
                    }
                }
            }
        }
    }
}
