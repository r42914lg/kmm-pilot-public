package r42914lg.trykmm.data.remote.dataSource

import r42914lg.trykmm.data.remote.service.AddressService
import r42914lg.trykmm.utils.runOperationCatching

class AddressDataSource (private val addressService: AddressService) {

    suspend fun getAddressList(token: String) = runOperationCatching {
        addressService.getAddressList(token)
    }
}