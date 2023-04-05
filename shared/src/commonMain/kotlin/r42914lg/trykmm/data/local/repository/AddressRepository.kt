package r42914lg.trykmm.data.local.repository

import r42914lg.trykmm.cache.Database

class AddressRepository(private val database: Database) {

    fun saveAddress(addressId: Int) {
        database.clearDatabase()
        database.insertLocalAddress(addressId)
    }

    fun getAddress() : Int? {
        return database.getLocalAddress()
    }
}