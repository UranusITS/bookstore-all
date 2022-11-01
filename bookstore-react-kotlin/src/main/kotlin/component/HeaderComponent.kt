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
import data.*
import kotlinext.js.js
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.html.style
import org.w3c.dom.HTMLInputElement
import org.w3c.fetch.INCLUDE
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
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
                    Link {
                        attrs.to = "/order"
                        userOutlined { }
                        +"个人订单"
                    }
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
                    menuItem {
                        attrs.disabled = false
                        Link {
                            attrs.to = "/book-edit"
                            plusCircleOutlined { }
                            +"新增图书"
                        }
                    }
                }
                menuItem {
                    Link {
                        attrs.to = "/"
                        logoutOutlined { }
                        +"退出登录"
                    }
                    attrs.onClick = {
                        logout()
                        localStorage.setItem("user", "")
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
        val localUser = getLocalUser()
        if (localUser != null) {
            setState {
                user = localUser
                isAuthored = true
            }
        }
    }

    private fun showModal() {
        setState { isModalVisible = true }
    }

    private fun modalCancel() {
        setState { isModalVisible = false }
    }

    private fun login() {
        GlobalScope.launch {
            val user = checkLogin(state.typedInName, state.typedInPassword)
            if (user != null) {
                if (user.auth_level!! < 0) {
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
                } else {
                    message.success("登录成功")
                    setLocalUser(user)
                    setState(
                        HeaderState(
                            user,
                            typedInName = "",
                            typedInPassword = "",
                            isAuthored = true,
                            isModalVisible = false
                        )
                    )
                }
            } else {
                message.error("登录失败")
            }
        }
    }

    private fun logout() {
        GlobalScope.launch {
            val username = state.user.username
            val response =
                window.fetch(
                    "$backendUrl/user/logout?username=${username}",
                    RequestInit(credentials = RequestCredentials.Companion.INCLUDE)
                )
                    .await()
                    .text()
                    .await()
            var seconds = response.toInt()
            val hours = seconds / 3600
            val minutes = seconds / 60 % 60
            seconds %= 60
            if (hours > 0) {
                message.success("退出登录成功，总登录时长${hours}时${minutes}分${seconds}秒")
            }
            else if (minutes > 0) {
                message.success("退出登录成功，总登录时长${minutes}分${seconds}秒")
            }
            else {
                message.success("退出登录成功，总登录时长${seconds}秒")
            }
        }
    }

    private fun register() {
        if (state.typedInName.isEmpty()) {
            message.error("请输入用户名")
            return
        }
        if (state.typedInPassword.length < 6) {
            message.error("密码长度不短于6位")
            return
        }
        GlobalScope.launch {
            val user = checkRegister(state.typedInName, state.typedInPassword)
            if (user != null) {
                message.success("注册成功")
                localStorage.setItem("id", user.id.toString())
                localStorage.setItem("name", user.username!!)
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
        console.log("rendering...")
        console.log("render here 1")
        header {
            console.log("render here 2")
            attrs.style = js {
                background = "white"
                position = "fixed"
                zIndex = 1
                width = "100%"
            }
            console.log("render here 3")
            div {
                attrs.style = js { float = "left" }
                console.log("render here 4")
                a {
                    attrs.href = ""
                    console.log("render here 5")
                    img {
                        attrs.src = "assets/logo.svg"
                        attrs.style = js { height = 48 }
                    }
                    console.log("render here 6")
                    img {
                        attrs.src = "assets/logo-name.svg"
                        attrs.style = js { height = 48 }
                    }
                }
            }
            console.log("render here 7")
            if (state.isAuthored) {
                div {
                    attrs.style = js { float = "right" }
                    dropdown {
                        attrs.overlay = buildMenu(state.user.auth_level!! > 0)
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
