package data

import kotlinx.serialization.Serializable


@Serializable
data class Address(
    val id: Int?,
    var user_id: Int,
    var name: String,
    var phone_number: String,
    var address_detail: String
) {
    constructor(): this(null, 0, "", "", "")
    constructor(user_id: Int): this(null, user_id, "", "", "")
    fun toAddressItem(): String {
        return "$name $phone_number $address_detail"
    }
}
