package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Address

interface AddressDao {
    fun getAddressById(id: Int): Address?

    fun getAddressByUserId(user_id: Int): List<Address>

    fun updateAllById(id: Int, name: String, phone_number: String, address_detail: String)

    fun save(address: Address): Address

    fun deleteById(id: Int)
}
