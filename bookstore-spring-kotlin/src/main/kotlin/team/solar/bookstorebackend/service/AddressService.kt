package team.solar.bookstorebackend.service

import team.solar.bookstorebackend.entity.Address

interface AddressService {
    fun getAddressById(id: Int): Address?

    fun getAddressByUserId(user_id: Int): List<Address>

    fun updateAddress(address: Address): Unit?

    fun addAddress(address: Address): Address

    fun deleteAddressById(id: Int)
}
