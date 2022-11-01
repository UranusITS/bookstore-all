package team.solar.bookstorebackend.daoimpl

import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.stereotype.Repository
import team.solar.bookstorebackend.dao.AddressDao
import team.solar.bookstorebackend.entity.Address
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.repository.AddressRepository

@Repository
class AddressDaoImpl(val repo: AddressRepository) : AddressDao {
    @Cacheable("address-id")
    override fun getAddressById(id: Int?): Address? = repo.getAddressById(id)

    @Cacheable("address-user", key = "#user.id")
    override fun getAddressByUser(user: User): List<Address> = repo.getAddressByUser(user)

    @Caching(
        put = [CachePut("address-id", key = "#address.id")],
        evict = [CacheEvict("address-user", key = "#address.user.id")]
    )
    override fun save(address: Address): Address = repo.save(address)

    @Caching(
        evict = [CacheEvict("address-id", key = "#id"), CacheEvict("address-user", allEntries = true)]
    )
    override fun deleteById(id: Int) = repo.deleteById(id)
}
