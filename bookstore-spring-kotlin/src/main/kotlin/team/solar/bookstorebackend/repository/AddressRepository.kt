package team.solar.bookstorebackend.repository

import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import team.solar.bookstorebackend.entity.Address

interface AddressRepository : CrudRepository<Address, Int> {
    fun getAddressById(id: Int): Address?

    @Query("select * from address where user_id = :user_id")
    fun getAddressByUserId(@Param("user_id") user_id: Int): List<Address>

    @Modifying
    @Query(
        "update address set name = :name, phone_number = :phone_number, " +
                "address_detail = :address_detail where id = :id"
    )
    fun updateAllById(
        @Param("id") id: Int,
        @Param("name") name: String,
        @Param("phone_number") phone_number: String,
        @Param("address_detail") address_detail: String
    )
}
