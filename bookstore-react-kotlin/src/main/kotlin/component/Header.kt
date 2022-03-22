package component

import antd.ChangeEventHandler
import antd.button.button
import antd.dropdown.dropdown
import antd.icon.*
import antd.layout.header
import antd.menu.menu
import antd.menu.menuItem
import antd.modal.modal
import antd.input.input
import antd.input.password
import antd.space.space
import kotlinx.html.style
import data.*
import react.*
import react.dom.a
import react.dom.div
import react.dom.img
import kotlinext.js.js
import kotlinx.browser.localStorage
import org.w3c.dom.HTMLInputElement

data class HeaderState(var user: User, var typedInName:String, var typedInPassword:String,
                       var isAuthored: Boolean, var isModalVisible: Boolean) : State

class Header(props: UserProps) : RComponent<UserProps, HeaderState>(props) {
    init {
        state = HeaderState(User(-1, ""), typedInName = "", typedInPassword = "", isAuthored = false, isModalVisible = false)
    }

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
                attrs.onClick = {
                    localStorage.setItem("id","")
                    localStorage.setItem("name","")
                    setState(HeaderState(User(-1, ""), state.typedInName, state.typedInPassword, isAuthored=false, isModalVisible=false))
                }
            }
        }
    }

    override fun componentDidMount() {
        val userID = localStorage.getItem("id")
        val userName = localStorage.getItem("name")
        if (userID != null && userID.isNotEmpty() && userID.toInt() != -1 && userName != null)
            setState(HeaderState(User(userID.toInt(), userName), typedInName = "", typedInPassword = "",
                isAuthored = true, isModalVisible = false))
    }

    private fun showModal() {
        setState(HeaderState(state.user, state.typedInName, state.typedInPassword, state.isAuthored, isModalVisible = true))
    }

    private fun modalCancel() {
        setState(HeaderState(state.user, state.typedInName, state.typedInPassword, state.isAuthored, isModalVisible = false))
    }

    private fun checkLogin(): Int {
        return 100
    }

    private fun login() {
        val id = checkLogin()
        if (id != -1) {
            localStorage.setItem("id", id.toString())
            localStorage.setItem("name", state.typedInName)
            setState(HeaderState(User(id, state.typedInName), typedInName = "", typedInPassword = "", isAuthored = true, isModalVisible=false))
        }
    }

    private fun checkRegister(): Int {
        return 100
    }

    private fun register() {
        val id = checkRegister()
        if (id != -1) {
            localStorage.setItem("id", id.toString())
            localStorage.setItem("name", state.typedInName)
            setState(HeaderState(User(id, state.typedInName), typedInName = "", typedInPassword = "", isAuthored = true, isModalVisible=false))
        }
    }

    private val nameInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState(HeaderState(state.user, it.target.asDynamic().value as String, state.typedInPassword, state.isAuthored, state.isModalVisible))
    }

    private val passwordInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState(HeaderState(state.user, state.typedInName, it.target.asDynamic().value as String, state.isAuthored, state.isModalVisible))
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
            if (state.isAuthored) {
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
            else {
                div {
                    attrs.style = js { float = "right" }
                    button {
                        attrs.onClick = {
                            showModal()
                        }
                        loginOutlined { }
                        +"登录"
                    }
                }
                modal {
                    attrs.title = "登录"
                    attrs.visible = state.isModalVisible
                    attrs.onCancel = {
                        modalCancel()
                    }
                    attrs.footer = null
                    div {
                        attrs.style = js {
                            width = 240
                            margin = "auto"
                        }
                        space {
                            attrs.direction = "vertical"
                            attrs.size = 20
                            input {
                                attrs.style = js { width = 240 }
                                attrs.placeholder = "请输入用户名"
                                attrs.onChange = nameInputChangeHandler
                                attrs.onPressEnter = {
                                    login()
                                }
                            }
                            password {
                                attrs.style = js { width = 240 }
                                attrs.placeholder = "请输入密码"
                                attrs.onChange = passwordInputChangeHandler
                                attrs.onPressEnter = {
                                    login()
                                }
                            }
                            button {
                                attrs.block = true
                                attrs.type = "primary"
                                attrs.onClick = {
                                    login()
                                }
                                +"登录"
                            }
                            button {
                                attrs.block = true
                                attrs.onClick = {
                                    register()
                                }
                                +"注册"
                            }
                        }
                    }
                }
            }
        }
    }
}
