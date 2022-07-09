package team.solar.bookstorebackend.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.entity.Address
import team.solar.bookstorebackend.entity.User

@Repository
interface AddressRepository : JpaRepository<Address, Int>, JpaSpecificationExecutor<Address> {
    fun getAddressById(id: Int?): Address?

    fun getAddressByUser(user: User): List<Address>
}
