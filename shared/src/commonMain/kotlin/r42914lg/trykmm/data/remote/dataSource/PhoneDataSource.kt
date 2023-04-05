package r42914lg.trykmm.data.remote.dataSource

import r42914lg.trykmm.data.remote.service.PhoneService
import r42914lg.trykmm.domain.remote.models.phone.Phone
import r42914lg.trykmm.utils.runOperationCatching

class PhoneDataSource (private val phoneService: PhoneService) {

    suspend fun sendPhone(phone: Phone) = runOperationCatching {
        phoneService.sendPhone(phone)
    }

    suspend fun getJwtToken(token: String, code: String) = runOperationCatching {
        phoneService.getJwtToken(token, code)
    }
}