package r42914lg.trykmm.ui

import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import r42914lg.trykmm.LocalPreferences
import r42914lg.trykmm.data.remote.dataSource.PhoneDataSource
import r42914lg.trykmm.data.remote.repository.PhoneRepository
import r42914lg.trykmm.data.remote.service.PhoneServiceTest
import r42914lg.trykmm.di.SimpleServiceLocator
import r42914lg.trykmm.domain.remote.models.phone.Phone
import r42914lg.trykmm.domain.remote.usecase.GetJwtTokenUseCase
import r42914lg.trykmm.domain.remote.usecase.SendPhoneUseCase
import r42914lg.trykmm.utils.doOnError
import r42914lg.trykmm.utils.doOnSuccess

class PhoneViewModel : ViewModel() {

    private val sendPhoneUseCase: SendPhoneUseCase
    private val getJwtTokenUseCase: GetJwtTokenUseCase
    private var localPrefs: LocalPreferences

    init {
        val phoneRepo = PhoneRepository(PhoneDataSource(PhoneServiceTest()))
        sendPhoneUseCase = SendPhoneUseCase(phoneRepo)
        getJwtTokenUseCase = GetJwtTokenUseCase(phoneRepo)

        localPrefs = LocalPreferences(SimpleServiceLocator.getDataStore(), viewModelScope)
    }

    private val _authenticationState = MutableStateFlow<AuthenticationState>(AuthenticationState.StateInitial).cMutableStateFlow()
    val authenticationState: CStateFlow<AuthenticationState>
        get() = _authenticationState.cStateFlow()

    fun authenticateWithPhone(phone: Phone) {
        viewModelScope.launch {
            sendPhoneUseCase.execute(phone)
                .doOnSuccess {
                    localPrefs.saveToken(it.token)
                    delay(500)
                    _authenticationState.value = AuthenticationState.StateValidPhone
                }
                .doOnError {
                    it.printStackTrace()
                    _authenticationState.value = AuthenticationState.StateError
                }
        }
    }

    fun onSmsEntered(sms: String) {
        viewModelScope.launch {
            getJwtTokenUseCase.execute(localPrefs.getToken().stateIn(viewModelScope).value, sms)
                .doOnSuccess {
                    localPrefs.saveJwtToken(it.access)
                    delay(500)
                    _authenticationState.value = AuthenticationState.StateValidSms
                }
                .doOnError {
                    it.printStackTrace()
                    _authenticationState.value = AuthenticationState.StateError
                }
            }
    }

    sealed interface AuthenticationState {
        object StateInitial : AuthenticationState
        object StateValidPhone : AuthenticationState
        object StateValidSms : AuthenticationState
        object StateError : AuthenticationState
    }
}