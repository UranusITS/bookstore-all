package data

import react.State


data class CartState(
    var cartItemList: List<CartItem>,
    var priceTotal: Double,
    var addressList: List<Address>,
    var isModalVisible: Boolean,
    var inputAddress: Address,
    var selectedAddressID: Int
) : State
