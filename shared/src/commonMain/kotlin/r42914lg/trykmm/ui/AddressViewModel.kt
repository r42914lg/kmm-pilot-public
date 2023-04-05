package r42914lg.trykmm.ui

import dev.icerock.moko.mvvm.flow.CStateFlow
import dev.icerock.moko.mvvm.flow.cMutableStateFlow
import dev.icerock.moko.mvvm.flow.cStateFlow
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import r42914lg.trykmm.LocalPreferences
import r42914lg.trykmm.data.local.repository.AddressRepository
import r42914lg.trykmm.data.remote.dataSource.AddressDataSource
import r42914lg.trykmm.data.remote.service.AddressServiceTest
import r42914lg.trykmm.di.SimpleServiceLocator
import r42914lg.trykmm.domain.local.usecase.GetAddressIdUseCase
import r42914lg.trykmm.domain.local.usecase.SaveAddressIdUseCase
import r42914lg.trykmm.domain.models.local.CheckableAddress
import r42914lg.trykmm.domain.models.local.toCheckableList
import r42914lg.trykmm.domain.remote.usecase.GetAddressListUseCase
import r42914lg.trykmm.utils.doOnError
import r42914lg.trykmm.utils.doOnSuccess

class AddressViewModel : ViewModel() {

    private val getAddressListUseCase: GetAddressListUseCase
    private val getAddressIdUseCase: GetAddressIdUseCase
    private val saveAddressIdUseCase: SaveAddressIdUseCase
    private var localPrefs: LocalPreferences

    private lateinit var listFromBack: List<CheckableAddress>

    init {
        val remoteAddressRepository =
            r42914lg.trykmm.data.remote.repository.AddressRepository(AddressDataSource(
                AddressServiceTest()
            ))
        getAddressListUseCase = GetAddressListUseCase(remoteAddressRepository)

        val localAddressRepository = AddressRepository(
            SimpleServiceLocator.getDatabase()
        )

        getAddressIdUseCase = GetAddressIdUseCase(localAddressRepository)
        saveAddressIdUseCase = SaveAddressIdUseCase(localAddressRepository)

        localPrefs = LocalPreferences(SimpleServiceLocator.getDataStore(), viewModelScope)

        viewModelScope.launch {
            getAddressListUseCase.execute(localPrefs.getJwtToken().stateIn(viewModelScope).value)
                .doOnSuccess {
                    listFromBack = it.toCheckableList()
                    setSelected(getAddressIdUseCase.execute() ?: -1, listFromBack)

                    _addressState.value = AddressState.StateContent(listFromBack)
                }
                .doOnError {
                    it.printStackTrace()
                    _addressState.value = AddressState.StateError
                }
        }
    }

    private val _addressState = MutableStateFlow<AddressState>(AddressState.StateLoading).cMutableStateFlow()
    val addressState: CStateFlow<AddressState>
        get() = _addressState.cStateFlow()

    fun onAddressSelected(addressId: Int) {

        saveAddressIdUseCase.execute(addressId)
        setSelected(addressId, listFromBack)
        _addressState.value = AddressState.StateContent(listFromBack)
    }

    private fun setSelected(addressId: Int, addrList: List<CheckableAddress>) {
        if (addressId == -1) {
            addrList[0].checked = true
            return
        }

        addrList.forEach {
            it.checked = (it.address.id == addressId)
        }
    }

    sealed interface AddressState {
        object StateLoading : AddressState
        object StateError : AddressState
        class StateContent(val addrList: List<CheckableAddress>) : AddressState
    }
}