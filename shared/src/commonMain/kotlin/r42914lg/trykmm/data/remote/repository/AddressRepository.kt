package r42914lg.trykmm.data.remote.repository

import r42914lg.trykmm.data.remote.dataSource.AddressDataSource

class AddressRepository (
    private val addressDataSource: AddressDataSource
) {

    suspend fun getAddressList(token: String)
        = addressDataSource.getAddressList(token)
}