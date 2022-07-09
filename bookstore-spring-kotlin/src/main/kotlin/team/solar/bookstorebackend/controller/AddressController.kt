package team.solar.bookstorebackend.controller

import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import team.solar.bookstorebackend.entity.Address
import team.solar.bookstorebackend.entity.User
import team.solar.bookstorebackend.service.AddressService

@RestController
@RequestMapping("/address")
class AddressController(val service: AddressService) {
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
