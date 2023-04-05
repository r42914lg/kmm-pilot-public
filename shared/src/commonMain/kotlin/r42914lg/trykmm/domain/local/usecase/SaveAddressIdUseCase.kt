package r42914lg.trykmm.domain.local.usecase

import r42914lg.trykmm.data.local.repository.AddressRepository

class SaveAddressIdUseCase(private val addressRepository: AddressRepository) {
    fun execute(addressId: Int) {
        addressRepository.saveAddress(addressId)
    }
}