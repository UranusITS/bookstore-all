package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.AddressDao
import team.solar.bookstorebackend.entity.Address
import team.solar.bookstorebackend.repository.AddressRepository

@Repository
class AddressDaoImpl(val repo: AddressRepository) : AddressDao {
    override fun getAddressById(id: Int): Address? = repo.getAddressById(id)

    override fun getAddressByUserId(user_id: Int): List<Address> = repo.getAddressByUserId(user_id)

    override fun updateAllById(id: Int, name: String, phone_number: String, address_detail: String) =
        repo.updateAllById(id, name, phone_number, address_detail)

    override fun save(address: Address): Address = repo.save(address)

    override fun deleteById(id: Int) = repo.deleteById(id)
}
