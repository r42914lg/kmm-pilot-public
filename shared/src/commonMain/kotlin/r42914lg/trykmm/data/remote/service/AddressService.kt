package r42914lg.trykmm.data.remote.service

import io.ktor.client.call.*
import io.ktor.client.request.*
import r42914lg.trykmm.ROOT_API_STAGE
import r42914lg.trykmm.di.SimpleServiceLocator
import r42914lg.trykmm.domain.remote.models.address.Address
import r42914lg.trykmm.domain.remote.models.address.Building
import r42914lg.trykmm.domain.remote.models.address.ResidentialComplex

interface AddressService {
    suspend fun getAddressList(token: String) : List<Address>
}

class AddressServiceImpl : AddressService {
    private val client = SimpleServiceLocator.getHttpClient()

    override suspend fun getAddressList(token: String) : List<Address> =
        client.get(ROOT_API_STAGE + "flats/") {
            url {
                header("Authorization", "Bearer $token")
            }
        }.body()
}

class AddressServiceTest : AddressService {
    override suspend fun getAddressList(token: String) : List<Address> =
        listOf(
            Address(1, "Main street, 29", 10, "100001", 100.0,
                Building("", "", ResidentialComplex(""))),

            Address(2, "California street, 50", 11, "100001", 100.0,
                Building("", "", ResidentialComplex(""))),

            Address(3, "Embarcadero street, 17", 12, "100001", 100.0,
                Building("", "", ResidentialComplex(""))),

            Address(4, "Wall street, 10", 13, "100001", 100.0,
                Building("", "", ResidentialComplex("")))
        )
}

