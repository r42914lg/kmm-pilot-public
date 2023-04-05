package r42914lg.trykmm.data.remote.repository

import r42914lg.trykmm.data.remote.dataSource.PhoneDataSource
import r42914lg.trykmm.domain.remote.models.phone.Phone

class PhoneRepository (private val phoneDataSource: PhoneDataSource) {

    suspend fun sendPhone(phone: Phone) =
        phoneDataSource.sendPhone(phone)

    suspend fun getJwtToken(token: String, code: String) =
        phoneDataSource.getJwtToken(token, code)
}