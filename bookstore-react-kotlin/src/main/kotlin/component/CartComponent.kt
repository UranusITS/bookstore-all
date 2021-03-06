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
import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.await
import kotlinx.coroutines.launch
import kotlinx.css.*
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
            message.error("??????????????????")
            return
        }
        val user = getLocalUser()
        if (user == null) {
            message.error("????????????")
            return
        }
        val orderItemList = mutableListOf<OrderItem>()
        for (item in state.cartItemList) {
            val book = getBookById(item.book!!.id!!)!!
            orderItemList.add(OrderItem(null, null, book.name, book.author, book.price, item.num, book.img_path))
        }
        val order = Order(user = user, address = Address(id = state.selectedAddressID), orderItems = orderItemList)
        addOrder(order)
        message.success("????????????")
        clearCartItems(user)
        fetchCartItems()
    }

    private suspend fun fetchCartItems() {
        val user = getLocalUser()
        if (user != null) {
            val cartItems = getCartItems(user)
            var newPriceTotal = .0
            for (cartItem in cartItems) {
                if (cartItem.checked == true) {
                    val book = cartItem.book
                    newPriceTotal += book?.price?.let { cartItem.num?.times(it) } ?: 0.0
                }
            }
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
                        extraInfo = "???????????????"
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
                                    +"?????????"
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
                                    +"???$priceTotal"
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
                                            +"????????????"
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            modal {
                attrs.title = "????????????"
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
                            attrs.placeholder = "????????????"
                            attrs.onChange = nameInputChangeHandler
                        }
                        input {
                            attrs.style = js { width = 240 }
                            attrs.placeholder = "????????????"
                            attrs.onChange = phoneNumberInputChangeHandler
                        }
                        input {
                            attrs.style = js { width = 240 }
                            attrs.placeholder = "????????????"
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
                                message.success("????????????")
                            }
                            +"??????"
                        }
                    }
                }
            }
        }
    }
}
