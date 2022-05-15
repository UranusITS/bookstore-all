package component

import antd.ChangeEventHandler
import antd.button.button
import antd.card.card
import antd.grid.col
import antd.grid.row
import antd.icon.*
import antd.input.input
import antd.input.password
import antd.layout.content
import antd.message.message
import antd.modal.modal
import antd.select.select
import antd.select.option
import antd.select.SelectComponent
import antd.space.space
import data.*
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.*
import kotlinext.js.js
import kotlinx.html.style
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.dom.HTMLInputElement
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
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
    init {
        state = CartState(listOf(), .0, listOf(), false, Address(), -1)
    }

    override fun componentDidMount() {
        val userID = localStorage.getItem("id")
        val newInputAddress = state.inputAddress
        newInputAddress.user_id = userID!!.toInt()
        setState { inputAddress = newInputAddress }
        GlobalScope.launch {
            fetchCartItems()
            fetchAddress()
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

    private suspend fun fetchCartItems() {
        val userID = localStorage.getItem("id")
        val response = window.fetch("http://localhost:8080/cart-item/items?user-id=$userID")
            .await()
            .text()
            .await()
        val newCartItemList = Json.decodeFromString<List<CartItem>>(response)
        var newPriceTotal = .0
        for (cartItem in newCartItemList) {
            if (cartItem.checked) {
                val bookResponse = window.fetch("http://localhost:8080/book/get-book-by-id?id=${cartItem.book_id}")
                    .await()
                    .text()
                    .await()
                val bookPrice = Json.decodeFromString<Book>(bookResponse).price
                newPriceTotal += cartItem.num * bookPrice
            }
        }
        setState {
            cartItemList = newCartItemList
            priceTotal = newPriceTotal
        }
    }

    private suspend fun fetchAddress() {
        val userID = localStorage.getItem("id")
        val response = window.fetch("http://localhost:8080/address/get-address-by-user-id?user-id=$userID")
            .await()
            .text()
            .await()
        val newAddressList = Json.decodeFromString<List<Address>>(response)
        setState { addressList = newAddressList }
    }

    private suspend fun addAddress() {
        val headers = Headers()
        headers.append("Content-Type", "application/json;charset=UTF-8")
        window.fetch(
            "http://localhost:8080/address/add-address",
            RequestInit(method = "POST", headers = headers, body = Json.encodeToString(state.inputAddress))
        ).await()
        fetchAddress()
    }

    private suspend fun addOrder() {
        val userID = localStorage.getItem("id")!!.toInt()
        val order = Order(null, userID, state.selectedAddressID)
        if (order.address_id == -1) {
            message.error("请先选择地址")
            return
        }
        val headers = Headers()
        headers.append("Content-Type", "application/json;charset=UTF-8")
        val orderID = window.fetch(
            "http://localhost:8080/order/add-order",
            RequestInit(method = "POST", headers = headers, body = Json.encodeToString(order))
        )
            .await()
            .text()
            .await()
            .toInt()
        val orderItemList = mutableListOf<OrderItem>()
        for (item in state.cartItemList) {
            val response = window.fetch("http://localhost:8080/book/get-book-by-id?id=${item.book_id}")
                .await()
                .text()
                .await()
            val book = Json.decodeFromString<Book>(response)
            orderItemList.add(OrderItem(null, orderID, book.name, book.author, book.price, item.num, book.img_path))
        }
        window.fetch(
            "http://localhost:8080/order-item/add-items",
            RequestInit(method = "POST", headers = headers, body = Json.encodeToString(orderItemList))
        )
        message.success("下单成功")
        window.fetch("http://localhost:8080/cart-item/delete-item?user-id=$userID")
            .await()
        fetchCartItems()
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
                    col {
                        attrs.span = 24
                        child(CartItemComponent::class) {
                            attrs {
                                id = item.id
                                userId = item.user_id
                                bookId = item.book_id
                                num = item.num
                                checked = item.checked
                                onPriceChange = priceChangeHandler
                            }
                        }
                    }
                }
            }
            row {
                attrs.style = kotlinext.js.js { marginTop = 64 }
                attrs.gutter = 24
                col {
                    card {
                        attrs.bordered = true
                        attrs.style = kotlinext.js.js {
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
                                var priceTotal = (state.priceTotal * 100).roundToInt().toDouble() / 100
                                +"￥${priceTotal}"
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
            modal {
                attrs.title = "新增地址"
                attrs.visible = state.isModalVisible
                attrs.onCancel = { modalCancel() }
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
                                GlobalScope.launch {
                                    addAddress()
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
