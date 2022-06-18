package team.solar.bookstorebackend.serviceimpl

import org.springframework.stereotype.Service
import team.solar.bookstorebackend.dao.AddressDao
import team.solar.bookstorebackend.entity.Address
import team.solar.bookstorebackend.service.AddressService

@Service
class AddressServiceImpl(val dao: AddressDao) : AddressService {
    override fun getAddressById(id: Int) = dao.getAddressById(id)

    override fun getAddressByUserId(user_id: Int) = dao.getAddressByUserId(user_id)

    override fun updateAddress(address: Address) =
        address.id?.let { dao.updateAllById(it, address.name, address.phone_number, address.address_detail) }

    override fun addAddress(address: Address) = dao.save(address)

    override fun deleteAddressById(id: Int) = dao.deleteById(id)
}
