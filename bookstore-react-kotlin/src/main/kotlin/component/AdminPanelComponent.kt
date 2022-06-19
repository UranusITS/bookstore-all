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
        state = AdminPanelState("user")
    }

    private var typeCount = 0

    private val handleClick: MenuClickEventHandler = { info ->
        console.log(info)
        //setState { curMenuItemIndex = info.key }
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
                            defaultSelectedKeys = arrayOf("user")
                        }
                        menuItem {
                            attrs.key = "user"
                            + "用户管理"
                        }
                        menuItem {
                            attrs.key = "statistic"
                            + "订单统计"
                        }
                    }
                }
                content {
                    if (state.curMenuItemKey == "user")
                        child(UserInfoComponent::class) { }
                    else if(state.curMenuItemKey == "statistic")
                        + "233"
                }
            }
        }
    }
}
