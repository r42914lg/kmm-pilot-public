package r42914lg.trykmm.domain.remote.usecase

import r42914lg.trykmm.data.remote.repository.PhoneRepository
import r42914lg.trykmm.domain.remote.models.phone.Phone

class SendPhoneUseCase (
    private val phoneRepository: PhoneRepository
) {

    suspend fun execute(phone: Phone) =
        phoneRepository.sendPhone(phone)
}