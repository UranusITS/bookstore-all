package component

import antd.ChangeEventHandler
import antd.button.button
import antd.dropdown.dropdown
import antd.icon.*
import antd.input.input
import antd.input.password
import antd.layout.header
import antd.menu.menu
import antd.menu.menuItem
import antd.message.message
import antd.modal.modal
import antd.space.space
import data.HeaderState
import data.User
import kotlinext.js.js
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.html.style
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import react.*
import react.dom.a
import react.dom.div
import react.dom.img
import react.router.dom.Link


class HeaderComponent(props: Props) : RComponent<Props, HeaderState>(props) {
    init {
        state = HeaderState(
            User(-1, "", "", -1),
            typedInName = "",
            typedInPassword = "",
            isAuthored = false,
            isModalVisible = false
        )
    }

    private fun buildMenu(is_admin: Boolean): ReactElement<*> {
        val menu = buildElement {
            menu {
                menuItem {
                    userOutlined { }
                    +"个人信息"
                }
                menuItem {
                    Link {
                        attrs.to = "/cart"
                        shoppingCartOutlined { }
                        +"购物车"
                    }
                }
                if (is_admin) {
                    menuItem {
                        attrs.disabled = false
                        Link {
                            attrs.to = "/admin"
                            settingOutlined { }
                            +"后台管理"
                        }
                    }
                }
                menuItem {
                    logoutOutlined { }
                    +"退出登录"
                    attrs.onClick = {
                        localStorage.setItem("id", "")
                        localStorage.setItem("name", "")
                        localStorage.setItem("authLevel", "")
                        setState {
                            user = User(-1, "", "", -1)
                            isAuthored = false
                            isModalVisible = false
                        }
                    }
                }
            }
        }
        return menu
    }

    override fun componentDidMount() {
        val userID = localStorage.getItem("id")
        val username = localStorage.getItem("name")
        val authLevel = localStorage.getItem("authLevel")
        if (userID != null && userID.isNotEmpty() && userID.toInt() != -1 && username != null
            && authLevel != null && authLevel.isNotEmpty() && authLevel.toInt() != -1
        )
            setState(
                HeaderState(
                    User(userID.toInt(), username, "", authLevel.toInt()), typedInName = "", typedInPassword = "",
                    isAuthored = true, isModalVisible = false
                )
            )
    }

    private fun showModal() {
        setState { isModalVisible = true }
    }

    private fun modalCancel() {
        setState { isModalVisible = false }
    }


    private suspend fun login() {
        val response =
            window.fetch(
                "http://localhost:8080/user/login?" +
                        "username=${state.typedInName}&password=${state.typedInPassword}"
            )
                .await()
                .text()
                .await()
        val user = Json.decodeFromString<User>(response)
        if (user.id != -1) {
            if (user.auth_level < 0) {
                message.error("您的账号已经被禁用")
                setState(
                    HeaderState(
                        User(-1, "", "", -1),
                        typedInName = "",
                        typedInPassword = "",
                        isAuthored = false,
                        isModalVisible = false
                    )
                )
                return
            }
            message.success("登录成功")
            localStorage.setItem("id", user.id.toString())
            localStorage.setItem("name", user.username)
            localStorage.setItem("authLevel", user.auth_level.toString())
            setState(
                HeaderState(
                    user,
                    typedInName = "",
                    typedInPassword = "",
                    isAuthored = true,
                    isModalVisible = false
                )
            )
        } else {
            message.error("登录失败")
        }
    }

    private suspend fun register() {
        if (state.typedInName.isEmpty()) {
            message.error("请输入用户名")
            return
        }
        if (state.typedInPassword.length < 6) {
            message.error("密码长度不短于6位")
            return
        }
        val response =
            window.fetch(
                "http://localhost:8080/user/register?" +
                        "username=${state.typedInName}&password=${state.typedInPassword}"
            )
                .await()
                .text()
                .await()
        val user = Json.decodeFromString<User>(response)
        if (user.id != -1) {
            message.success("注册成功")
            localStorage.setItem("id", user.id.toString())
            localStorage.setItem("name", user.username)
            localStorage.setItem("authLevel", user.auth_level.toString())
            setState(
                HeaderState(
                    user,
                    typedInName = "",
                    typedInPassword = "",
                    isAuthored = true,
                    isModalVisible = false
                )
            )
        } else {
            message.error("注册失败")
        }
    }

    private val nameInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState(
            HeaderState(
                state.user,
                it.target.asDynamic().value as String,
                state.typedInPassword,
                state.isAuthored,
                state.isModalVisible
            )
        )
    }

    private val passwordInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        setState(
            HeaderState(
                state.user,
                state.typedInName,
                it.target.asDynamic().value as String,
                state.isAuthored,
                state.isModalVisible
            )
        )
    }

    override fun RBuilder.render() {
        header {
            attrs.style = js {
                background = "white"
                position = "fixed"
                zIndex = 1
                width = "100%"
            }
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
                        attrs.overlay = buildMenu(state.user.auth_level > 0)
                        button {
                            +"Hello, ${state.user.username}"
                            downOutlined { }
                        }
                    }
                }
            } else {
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
                                    GlobalScope.launch {
                                        login()
                                    }
                                }
                            }
                            password {
                                attrs.style = js { width = 240 }
                                attrs.placeholder = "请输入密码"
                                attrs.onChange = passwordInputChangeHandler
                                attrs.onPressEnter = {
                                    GlobalScope.launch {
                                        login()
                                    }
                                }
                            }
                            button {
                                attrs.block = true
                                attrs.type = "primary"
                                attrs.onClick = {
                                    GlobalScope.launch {
                                        login()
                                    }
                                }
                                +"登录"
                            }
                            button {
                                attrs.block = true
                                attrs.onClick = {
                                    GlobalScope.launch {
                                        register()
                                    }
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
