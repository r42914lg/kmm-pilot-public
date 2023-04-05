package r42914lg.trykmm.domain.local.usecase

import r42914lg.trykmm.data.local.repository.AddressRepository

class GetAddressIdUseCase(private val addressRepository: AddressRepository) {
    fun execute() : Int? {
        return addressRepository.getAddress()
    }
}