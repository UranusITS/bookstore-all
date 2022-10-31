package component

import antd.layout.content
import antd.layout.layout
import antd.layout.sider
import antd.menu.MenuClickEventHandler
import antd.menu.menu
import antd.menu.menuItem
import data.AdminPanelState
import react.*


class AdminPanelComponent(props: Props) : RComponent<Props, AdminPanelState>(props) {
    init {
        state = AdminPanelState("auth")
    }

    private val handleClick: MenuClickEventHandler = { info ->
        console.log(info)
        setState { curMenuItemKey = info.key as String }
    }

    override fun RBuilder.render() {
        content {
            attrs.style = kotlinext.js.js {
                width = 1280
                margin = "120px auto"
            }
            layout {
                sider {
                    attrs.style = kotlinext.js.js {
                        background = "rgb(240, 242, 245)"
                    }
                    menu {
                        attrs {
                            mode = "inline"
                            defaultOpenKeys = arrayOf("type")
                            onClick = handleClick
                            defaultSelectedKeys = arrayOf("auth")
                        }
                        menuItem {
                            attrs.key = "auth"
                            + "用户权限"
                        }
                        menuItem {
                            attrs.key = "order"
                            + "订单统计"
                        }
                        menuItem {
                            attrs.key = "consumption"
                            + "消费统计"
                        }
                    }
                }
                content {
                    when (state.curMenuItemKey) {
                        "auth" -> child(UserInfoComponent::class) { }
                        "order" -> child(OrderListAdminComponent::class) { }
                        "consumption" -> child(UserConsumptionComponent::class) { }
                    }
                }
            }
        }
    }
}
