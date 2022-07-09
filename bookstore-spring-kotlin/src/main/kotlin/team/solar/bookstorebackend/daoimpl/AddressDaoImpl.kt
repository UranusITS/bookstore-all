package team.solar.bookstorebackend.daoimpl

import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.AddressDao
import team.solar.bookstorebackend.entity.Address
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.repository.AddressRepository

@Repository
class AddressDaoImpl(val repo: AddressRepository) : AddressDao {
    override fun getAddressById(id: Int?): Address? = repo.getAddressById(id)

    override fun getAddressByUser(user: User): List<Address> = repo.getAddressByUser(user)

    override fun save(address: Address): Address = repo.save(address)

    override fun deleteById(id: Int) = repo.deleteById(id)
}
