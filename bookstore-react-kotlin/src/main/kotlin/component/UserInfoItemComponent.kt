package component

import antd.button.button
import antd.card.card
import antd.icon.deleteOutlined
import antd.icon.saveOutlined
import antd.message.message
import antd.select.option
import antd.select.SelectComponent
import antd.select.select
import data.*
import kotlinext.js.js
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.RBuilder
import react.RComponent
import react.buildElement
import style.UserInfoItemStyles
import styled.*


class UserInfoItemComponent(props: UserProps) : RComponent<UserProps, UserState>(props) {
    init {
        state = UserState(props)
    }

    private val handleAuthLevelChange = { value: String, _: Any ->
        setState(UserState(state.id, state.username, value.toInt()))
    }

    override fun RBuilder.render() {
        val adminLevel = localStorage.getItem("authLevel")!!.toInt()
        styledDiv {
            card {
                attrs.bordered = true
                attrs.style = js {
                    margin = "0 auto"
                    width = 960
                }
                styledDiv {
                    css {
                        + UserInfoItemStyles.inline
                        + UserInfoItemStyles.userId
                    }
                    +state.id.toString()
                }
                styledDiv {
                    css {
                        + UserInfoItemStyles.inline
                        + UserInfoItemStyles.userName
                    }
                    +state.username
                }
                styledDiv {
                    css {
                        + UserInfoItemStyles.inline
                        + UserInfoItemStyles.authLevel
                    }
                    select<String, SelectComponent<String>> {
                        attrs.style = js {
                            margin = "0 auto"
                            width = 240
                        }
                        attrs.defaultValue = state.auth_level.toString()
                        attrs.onChange = handleAuthLevelChange
                        if (state.auth_level >= adminLevel)
                            attrs.disabled = true
                        option {
                            attrs.value = "2"
                            + "超级管理员"
                            if (adminLevel <= 2)
                                attrs.disabled = true
                        }
                        option {
                            attrs.value = "1"
                            + "管理员"
                            if (adminLevel <= 1)
                                attrs.disabled = true
                        }
                        option {
                            attrs.value = "0"
                            + "用户"
                            if (adminLevel <= 0)
                                attrs.disabled = true
                        }
                        option {
                            attrs.value = "-1"
                            + "封禁"
                            if (adminLevel <= -1)
                                attrs.disabled = true
                        }
                    }
                }
                styledDiv {
                    css {
                        + UserInfoItemStyles.inline
                        + UserInfoItemStyles.editButton
                    }
                    button {
                        attrs.size = "large"
                        if (state.auth_level >= adminLevel)
                            attrs.disabled = true
                        attrs.icon = buildElement {
                            saveOutlined { }
                        }
                        attrs.onClick = {
                            GlobalScope.launch {
                                val headers = Headers()
                                headers.append("Content-Type", "application/json;charset=UTF-8")
                                val response = window.fetch(
                                    "http://localhost:8080/user/update-user-auth",
                                    RequestInit(
                                        method = "POST",
                                        headers = headers,
                                        body = Json.encodeToString(User(state))
                                    )
                                )
                                    .await()
                                    .text()
                                    .await()
                                message.success("保存成功")
                            }
                        }
                    }
                    button {
                        attrs.size = "large"
                        attrs.danger = true
                        attrs.type = "primary"
                        if (state.auth_level >= adminLevel)
                            attrs.disabled = true
                        attrs.icon = buildElement {
                            deleteOutlined { }
                        }
                        attrs.onClick = {
                            console.log("删除尝试")
                            /**
                            GlobalScope.launch {
                                val headers = Headers()
                                headers.append("Content-Type", "application/json;charset=UTF-8")
                                val response = window.fetch(
                                    "http://localhost:8080/user/delete",
                                    RequestInit(
                                        method = "POST",
                                        headers = headers,
                                        body = Json.encodeToString(User(state))
                                    )
                                )
                                    .await()
                                    .text()
                                    .await()
                                message.success("删除成功")
                            }
                            */
                        }
                    }
                }
            }
        }
    }
}
