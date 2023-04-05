package r42914lg.trykmm.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import r42914lg.trykmm.httpClient
import r42914lg.trykmm.cache.Database
import r42914lg.trykmm.cache.DatabaseDriverFactory
import kotlin.native.concurrent.ThreadLocal

@ThreadLocal
object SimpleServiceLocator {

    private lateinit var database: Database
    private lateinit var driverFactory: DatabaseDriverFactory
    private lateinit var dataStore: DataStore<Preferences>
    private val httpClient = httpClient()

    fun getHttpClient() = httpClient

    fun setDriverFactory(driverFactory: DatabaseDriverFactory) {
        SimpleServiceLocator.driverFactory = driverFactory
    }

    fun setDataStore(dataStore: DataStore<Preferences>) {
        SimpleServiceLocator.dataStore = dataStore
    }

    fun getDatabase(): Database =
        if (SimpleServiceLocator::database.isInitialized)
            database
        else if (SimpleServiceLocator::driverFactory.isInitialized) {
            database = Database(driverFactory)
            database
        } else
            throw IllegalStateException("Native driver factory must be set before obtaining a reference")

    fun getDataStore(): DataStore<Preferences> =
        if (SimpleServiceLocator::dataStore.isInitialized)
            dataStore
        else
            throw IllegalStateException("Native data store must be set before obtaining a reference")
}