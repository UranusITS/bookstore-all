package data

import kotlinx.browser.localStorage
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit
import react.setState


@Serializable
data class Address(
    val id: Int? = null,
    var user: User? = null,
    var name: String? = null,
    var phone_number: String? = null,
    var address_detail: String? = null
) {
    constructor(user_id: Int) : this(null, User(id=user_id), "", "", "")

    fun toAddressItem(): String {
        return "$name $phone_number $address_detail"
    }
}

suspend fun getAddress(user: User): List<Address> {
    val response = window.fetch("$backendUrl/address/get-address-by-user-id?user-id=${user.id}")
        .await()
        .text()
        .await()
    return Json.decodeFromString<List<Address>>(response)
}

suspend fun addAddress(address: Address) {
    val headers = Headers()
    headers.append("Content-Type", "application/json;charset=UTF-8")
    window.fetch(
        "$backendUrl/address/add-address",
        RequestInit(method = "POST", headers = headers, body = Json.encodeToString(address))
    ).await()
}
