package team.solar.bookstorebackend.dao

import team.solar.bookstorebackend.entity.Address
import team.solar.bookstorebackend.entity.User

interface AddressDao {
    fun getAddressById(id: Int?): Address?

    fun getAddressByUser(user: User): List<Address>

    fun save(address: Address): Address

    fun deleteById(id: Int)
}
