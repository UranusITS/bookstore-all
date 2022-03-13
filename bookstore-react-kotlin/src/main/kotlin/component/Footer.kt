package component

import antd.layout.footer
import data.*
import react.*
import react.dom.a
import react.dom.p

val Footer = fc<Props> {
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
