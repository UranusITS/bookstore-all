package team.solar.bookstorebackend

import org.springframework.data.annotation.Id
import org.springframework.data.jdbc.repository.query.Modifying
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*


@Table("address")
data class Address(
    @Id val id: Int?,
    val user_id: Int,
    val name: String,
    val phone_number: String,
    val address_detail: String
)

interface AddressRepository : CrudRepository<Address, Int> {
    @Query("select * from address where id = :id")
    fun getAddressById(@Param("id") id: Int): Address?

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

@Service
class AddressService(val db: AddressRepository) {
    fun getAddressById(id: Int) = db.getAddressById(id)

    fun getAddressByUserId(user_id: Int) = db.getAddressByUserId(user_id)

    fun updateAddress(address: Address) =
        address.id?.let { db.updateAllById(it, address.name, address.phone_number, address.address_detail) }

    fun addAddress(address: Address) = db.save(address)

    fun deleteAddressById(id: Int) = db.deleteById(id)
}

@RestController
@RequestMapping("/address")
class AddressResource(val service: AddressService) {
    @RequestMapping("/get-address-by-id")
    fun getAddressById(@RequestParam("id") id: Int) = service.getAddressById(id)

    @RequestMapping("/get-address-by-user-id")
    fun getAddressByUserId(@RequestParam("user-id") user_id: Int) = service.getAddressByUserId(user_id)

    @RequestMapping("/update-address")
    fun updateAddress(@RequestBody address: Address) = service.updateAddress(address)

    @RequestMapping("/add-address")
    fun addAddress(@RequestBody address: Address) = service.addAddress(address)

    @RequestMapping("/delete-address-by-id")
    fun deleteAddressById(@RequestParam("id") id: Int) = service.deleteAddressById(id)
}
