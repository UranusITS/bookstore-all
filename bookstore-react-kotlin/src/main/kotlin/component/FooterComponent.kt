package component

import antd.layout.footer
import react.Props
import react.dom.a
import react.dom.p
import react.fc

val FooterComponent = fc<Props> {
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
