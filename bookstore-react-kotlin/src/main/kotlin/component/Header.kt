package component

import antd.button.button
import antd.dropdown.dropdown
import antd.icon.*
import antd.layout.header
import antd.menu.menu
import antd.menu.menuItem
import kotlinx.html.style
import data.*
import react.*
import react.dom.a
import react.dom.div
import react.dom.img
import kotlinext.js.js

private val menu = buildElement {
    menu {
        menuItem {
            userOutlined { }
            +"个人信息"
        }
        menuItem {
            shoppingCartOutlined { }
            +"购物车"
        }
        menuItem {
            logoutOutlined { }
            +"退出登录"
        }
    }
}


class Header (props: UserProps) : RComponent<UserProps, UserState>(props) {
    init {
        state = UserState(props)
    }

    override fun RBuilder.render() {
        header {
            attrs.style = js { background = "white" }
            div {
                attrs.style = js { float = "left" }
                a {
                    attrs.href = ""
                    img {
                        attrs.src = "assets/logo.svg"
                        attrs.style = js { height = 48 }
                    }
                    img {
                        attrs.src = "assets/logo-name.svg"
                        attrs.style = js { height = 48 }
                    }
                }
            }
            div {
                attrs.style = js { float = "right" }
                dropdown {
                    attrs.overlay = menu
                    button {
                        +"Hello, ${state.user.name}"
                        downOutlined { }
                    }
                }
            }
        }
    }

}
