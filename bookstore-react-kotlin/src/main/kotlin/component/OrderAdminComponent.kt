package component

import antd.button.button
import antd.card.card
import antd.grid.col
import antd.grid.row
import antd.icon.infoCircleOutlined
import antd.modal.modal
import data.OrderAdminState
import data.OrderProps
import data.getOrderById
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import react.RBuilder
import react.RComponent
import react.buildElement
import react.setState
import style.OrderAdminStyles
import style.SettlementItemStyles
import styled.css
import styled.styledDiv
import styled.styledP
import kotlin.math.roundToInt

class OrderAdminComponent(props: OrderProps) : RComponent<OrderProps, OrderAdminState>(props) {
    init {
        state = OrderAdminState(props.id, null, null, listOf(), false)
    }

    override fun componentDidMount() {
        GlobalScope.launch {
            val order = getOrderById(props.id)
            if (order != null) {
                setState(OrderAdminState(order.id!!, order.user, order.address, order.orderItems!!, false))
            }
        }
    }

    private fun modalCancel() {
        setState { isModalVisible = false }
    }

    override fun RBuilder.render() {
        if (state.address != null && state.user != null) {
            styledDiv {
                card {
                    attrs.bordered = true
                    attrs.style = kotlinext.js.js {
                        margin = "0 auto"
                        width = 960
                    }
                    styledDiv {
                        css { +OrderAdminStyles.inline }
                        styledP {
                            css { +OrderAdminStyles.orderId }
                            +state.id.toString()
                        }
                    }
                    styledDiv {
                        css { +OrderAdminStyles.inline }
                        styledP {
                            css { +OrderAdminStyles.username }
                            +state.user!!.username!!
                        }
                    }
                    styledDiv {
                        css { +OrderAdminStyles.inline }
                        styledP {
                            css { +OrderAdminStyles.address }
                            +state.address!!.toAddressItem()
                        }
                    }
                    styledDiv {
                        css {
                            +OrderAdminStyles.inline
                            +OrderAdminStyles.infoIcon
                        }
                        button {
                            attrs.size = "large"
                            attrs.icon = buildElement {
                                infoCircleOutlined { }
                            }
                            attrs.onClick = {
                                setState { isModalVisible = true }
                            }
                        }
                    }
                }
            }
        }
        modal {
            attrs.title = "订单编号 #${state.id}"
            attrs.visible = state.isModalVisible
            attrs.onCancel = { modalCancel() }
            attrs.footer = null
            attrs.width = 860
            var priceTotal = 0.0
            row {
                attrs.gutter = 24
                for (item in state.orderItems) {
                    col {
                        attrs.span = 24
                        OrderItemComponent {
                            attrs {
                                id = item.id!!
                                name = item.name!!
                                author = item.author!!
                                price = item.price!!
                                num = item.num!!
                                img_path = item.img_path!!
                            }
                        }
                    }
                    priceTotal += item.num!! * item.price!!
                }
            }
            row {
                attrs.style = kotlinext.js.js { marginTop = 32 }
                attrs.gutter = 24
                col {
                    card {
                        attrs.bordered = true
                        attrs.style = kotlinext.js.js {
                            margin = "0 auto"
                            width = 800
                        }
                        styledDiv {
                            css {
                                +SettlementItemStyles.inline
                                +SettlementItemStyles.bottomMargin
                            }
                            styledP {
                                css { +SettlementItemStyles.bottomMargin }
                                +"总价："
                            }
                        }
                        styledDiv {
                            css {
                                +SettlementItemStyles.inline
                                +SettlementItemStyles.bottomMargin
                                width = 200.px
                            }
                            styledP {
                                css {
                                    +SettlementItemStyles.bottomMargin
                                    color = Color.red
                                    fontSize = 18.pt
                                }
                                priceTotal = (priceTotal * 100).roundToInt().toDouble() / 100
                                +"￥${priceTotal}"
                            }
                        }
                    }
                }
            }
        }
    }
}
