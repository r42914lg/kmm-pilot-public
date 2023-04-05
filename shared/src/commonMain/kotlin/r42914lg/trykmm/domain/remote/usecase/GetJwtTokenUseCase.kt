package r42914lg.trykmm.domain.remote.usecase

import r42914lg.trykmm.data.remote.repository.PhoneRepository

class GetJwtTokenUseCase (
    private val phoneRepository: PhoneRepository
) {

    suspend fun execute(token: String, code: String) =
        phoneRepository.getJwtToken(token, code)
}