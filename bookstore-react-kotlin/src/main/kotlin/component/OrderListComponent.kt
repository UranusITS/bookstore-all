package component

import antd.grid.col
import antd.grid.row
import antd.layout.content
import data.OrderListState
import data.getLocalUser
import data.getOrdersByUser
import kotlinext.js.js
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.px
import react.Props
import react.RBuilder
import react.RComponent
import react.setState


class OrderListComponent(props: Props) : RComponent<Props, OrderListState>(props) {
    init {
        state = OrderListState(listOf())
    }

    override fun componentDidMount() {
        val user = getLocalUser()
        if (user != null) {
            GlobalScope.launch {
                val orders = getOrdersByUser(user)
                console.log(orders)
                setState { orderList = orders }
            }
        }
    }

    override fun RBuilder.render() {
        content {
            attrs.style = js {
                width = 1080.px
                margin = "120px auto"
            }
            if (state.orderList.isEmpty()) {
                errorComponent {
                    attrs {
                        errorCode = 404
                        extraInfo = "订单为空"
                    }
                }
            }
            else {
                row {
                    attrs.gutter = 24
                    for (order in state.orderList) {
                        console.log(order)
                        col {
                            attrs.span = 24
                            child(OrderComponent::class) {
                                attrs {
                                    id = order.id!!
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
