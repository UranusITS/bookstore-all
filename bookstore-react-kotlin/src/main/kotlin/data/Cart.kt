package data

import react.State


data class CartState(
    var cartItemList: List<CartItem>,
    var priceTotal: Double
) : State
