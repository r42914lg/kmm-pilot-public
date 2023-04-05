package r42914lg.trykmm.domain.remote.models.address

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Address(
    val id: Int,
    val number: String,
    val floor: Int,
    val account_number: String,
    val area: Double,
    val building: Building
)

@Serializable
data class Building (
    val address: String?,
    val entrance: String,
    @SerialName("residential_complex")
    val residentialComplex: ResidentialComplex
)

@Serializable
data class ResidentialComplex (
    val name: String
)