package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.AddressDao
import team.solar.bookstorebackend.entity.Address
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.service.AddressService
import javax.transaction.Transactional

@Service
class AddressServiceImpl(val dao: AddressDao) : AddressService {
    override fun getAddressById(id: Int) = dao.getAddressById(id)

    override fun getAddressByUserId(user_id: Int) = dao.getAddressByUser(User(id=user_id))

    @Transactional
    override fun updateAddress(address: Address) {
        val oldAddress = dao.getAddressById(address.id)
        if (oldAddress != null) {
            dao.save(address)
        }
    }

    override fun addAddress(address: Address) = dao.save(address)

    override fun deleteAddressById(id: Int) = dao.deleteById(id)
}
