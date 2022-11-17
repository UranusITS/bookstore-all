package component

import antd.ChangeEventHandler
import antd.button.button
import antd.card.card
import antd.grid.col
import antd.grid.row
import antd.icon.payCircleOutlined
import antd.icon.plusOutlined
import antd.input.input
import antd.layout.content
import antd.message.message
import antd.modal.modal
import antd.select.SelectComponent
import antd.select.option
import antd.select.select
import antd.space.space
import data.*
import kotlinext.js.js
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinx.html.style
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.WebSocket
import react.Props
import react.RBuilder
import react.RComponent
import react.dom.div
import react.setState
import style.SettlementItemStyles
import styled.css
import styled.styledDiv
import styled.styledP
import styled.styledSpan
import kotlin.math.roundToInt


class CartComponent(props: Props) : RComponent<Props, CartState>(props) {
    private var socket: WebSocket? = null

    init {
        state = CartState(listOf(), .0, listOf(), false, Address(), -1)
        val user = getLocalUser()
        if (user != null) {
            socket = WebSocket("$websocketUrl/websocket/${user.username}")
            socket!!.onopen = {
                console.log("websocket 连接成功")
                socket!!.send("hello from ${user.id}")
            }
            socket!!.onclose = {
                console.log("websocket 连接关闭")
            }
            socket!!.onmessage = {
                console.log(it)
                console.log(it.data)
                when (it.data) {
                    "SUCCESS" -> {
                        message.success("下单成功")
                        GlobalScope.launch {
                            clearCartItems(user)
                            fetchCartItems()
                        }
                    }
                    "FAILED" -> {
                        message.error("下单失败")
                    }
                    else -> {
                        message.error("下单失败")
                    }
                }
            }
        }
    }

    override fun componentDidMount() {
        val user = getLocalUser()
        if (user != null) {
            val newInputAddress = state.inputAddress
            newInputAddress.user = user
            setState { inputAddress = newInputAddress }
            GlobalScope.launch {
                fetchCartItems()
                val address = getAddress(user)
                setState { addressList = address }
            }
        }
    }

    private fun showModal() {
        setState { isModalVisible = true }
    }

    private fun modalCancel() {
        setState { isModalVisible = false }
    }

    private val nameInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        val newInputAddress = state.inputAddress
        newInputAddress.name = it.target.asDynamic().value as String
        setState { inputAddress = newInputAddress }
    }

    private val phoneNumberInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        val newInputAddress = state.inputAddress
        newInputAddress.phone_number = it.target.asDynamic().value as String
        setState { inputAddress = newInputAddress }
    }

    private val addressDetailInputChangeHandler: ChangeEventHandler<HTMLInputElement> = {
        val newInputAddress = state.inputAddress
        newInputAddress.address_detail = it.target.asDynamic().value as String
        setState { inputAddress = newInputAddress }
    }

    private val addressSelectChangHandler = fun(value: Int, _: Any) {
        setState { selectedAddressID = value }
    }

    private suspend fun addOrder() {
        if (state.selectedAddressID == -1) {
            message.error("请先选择地址")
            return
        }
        val user = getLocalUser()
        if (user == null) {
            message.error("请先登录")
            return
        }
        val orderItemList = mutableListOf<OrderItem>()
        for (item in state.cartItemList) {
            val book = getBookById(item.book!!.id!!)!!
            orderItemList.add(OrderItem(null, null, book.name, book.author, book.price, item.num, book.img_path))
        }
        val order = Order(user = user, address = Address(id = state.selectedAddressID), orderItems = orderItemList)
        addOrder(order)
    }

    private suspend fun fetchCartItems() {
        val user = getLocalUser()
        if (user != null) {
            val cartItems = getCartItems(user)
            var newPriceTotal = getCartPrice(cartItems)
            /**
            var newPriceTotal = .0
            for (cartItem in cartItems) {
                if (cartItem.checked == true) {
                    val book = cartItem.book
                    newPriceTotal += book?.price?.let { cartItem.num?.times(it) } ?: 0.0
                }
            }
            **/
            setState {
                cartItemList = cartItems
                priceTotal = newPriceTotal
            }
        }
    }

    private val priceChangeHandler = {
        GlobalScope.launch {
            fetchCartItems()
        }
    }

    override fun RBuilder.render() {
        content {
            attrs.style = js {
                width = 1080.px
                margin = "120px auto"
            }
            row {
                attrs.gutter = 24
                for (item in state.cartItemList) {
                    console.log(item)
                    col {
                        attrs.span = 24
                        child(CartItemComponent::class) {
                            attrs {
                                id = item.id
                                userId = item.user!!.id!!
                                bookId = item.book!!.id!!
                                num = item.num!!
                                checked = item.checked!!
                                onPriceChange = priceChangeHandler
                            }
                        }
                    }
                }
            }
            if (state.cartItemList.isEmpty()) {
                errorComponent {
                    attrs {
                        errorCode = 404
                        extraInfo = "购物车为空"
                    }
                }
            } else {
                row {
                    attrs.style = js { marginTop = 64 }
                    attrs.gutter = 24
                    col {
                        card {
                            attrs.bordered = true
                            attrs.style = js {
                                margin = "0 auto"
                                width = 1200
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
                                    val priceTotal = (state.priceTotal * 100).roundToInt().toDouble() / 100
                                    +"￥$priceTotal"
                                }
                            }
                            styledDiv {
                                css {
                                    +SettlementItemStyles.inline
                                    +SettlementItemStyles.bottomMargin
                                }
                                styledDiv {
                                    css {
                                        width = 800.px
                                        textAlign = TextAlign.right
                                    }
                                    styledSpan {
                                        css {
                                            marginRight = 16.px
                                        }
                                        select<Int, SelectComponent<Int>> {
                                            attrs.style = js { width = 480.px }
                                            attrs.onChange = addressSelectChangHandler
                                            for (address in state.addressList) {
                                                option {
                                                    attrs.value = address.id!!
                                                    +address.toAddressItem()
                                                }
                                            }
                                        }
                                    }
                                    styledSpan {
                                        css {
                                            marginRight = 56.px
                                        }
                                        button {
                                            attrs.size = "small"
                                            attrs.onClick = { showModal() }
                                            plusOutlined { }
                                        }
                                    }
                                    styledSpan {
                                        button {
                                            attrs {
                                                style = js {
                                                    width = 160.px
                                                    margin = "0 auto"
                                                }
                                                type = "primary"
                                                size = "large"
                                            }
                                            attrs.onClick = {
                                                GlobalScope.launch {
                                                    addOrder()
                                                }
                                            }
                                            payCircleOutlined { }
                                            +"立即购买"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            modal {
                attrs.title = "新增地址"
                attrs.visible = state.isModalVisible
                attrs.onCancel = { modalCancel() }
                attrs.footer = null
                div {
                    attrs.style = js {
                        width = 240
                        margin = "auto"
                    } as String
                    space {
                        attrs.direction = "vertical"
                        attrs.size = 20
                        input {
                            attrs.style = js { width = 240 }
                            attrs.placeholder = "真实姓名"
                            attrs.onChange = nameInputChangeHandler
                        }
                        input {
                            attrs.style = js { width = 240 }
                            attrs.placeholder = "联系电话"
                            attrs.onChange = phoneNumberInputChangeHandler
                        }
                        input {
                            attrs.style = js { width = 240 }
                            attrs.placeholder = "邮寄地址"
                            attrs.onChange = addressDetailInputChangeHandler
                        }
                        button {
                            attrs.block = true
                            attrs.type = "primary"
                            attrs.onClick = {
                                val user = getLocalUser()
                                if (user != null) {
                                    GlobalScope.launch {
                                        addAddress(state.inputAddress)
                                        val address = getAddress(user)
                                        setState { addressList = address }
                                    }
                                }
                                modalCancel()
                                message.success("添加成功")
                            }
                            +"添加"
                        }
                    }
                }
            }
        }
    }
}
