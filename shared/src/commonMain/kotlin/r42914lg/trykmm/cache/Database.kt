package r42914lg.trykmm.cache

class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = MyDb(databaseDriverFactory.createDriver())
    private val dbQuery = database.darsDbQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeLocalAddress()
        }
    }

    internal fun getLocalAddress(): Int? {
        return dbQuery.selectLocalAddressById().executeAsOneOrNull()?.address_id?.toInt()
    }

    internal fun insertLocalAddress(addressId: Int) {
        dbQuery.insertLocalAddress(addressId.toLong())
    }
}