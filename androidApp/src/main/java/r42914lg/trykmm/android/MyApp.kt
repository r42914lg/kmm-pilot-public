package r42914lg.trykmm.android

import android.app.Application
import r42914lg.trykmm.di.SimpleServiceLocator
import r42914lg.trykmm.getDataStore
import r42914lg.trykmm.cache.DatabaseDriverFactory

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        SimpleServiceLocator.setDriverFactory(DatabaseDriverFactory(this))
        SimpleServiceLocator.setDataStore(getDataStore(this))
    }
}