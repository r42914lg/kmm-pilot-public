package r42914lg.trykmm.domain.remote.usecase

import r42914lg.trykmm.data.remote.repository.AddressRepository

class GetAddressListUseCase constructor(
    private val addressRepository: AddressRepository
){

    suspend fun execute(token: String) =
        addressRepository.getAddressList(token)
}