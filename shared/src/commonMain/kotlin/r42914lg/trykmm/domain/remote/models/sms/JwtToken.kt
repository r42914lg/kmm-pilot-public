package r42914lg.trykmm.domain.remote.models.sms

import kotlinx.serialization.Serializable

@Serializable
data class JwtToken(
    val access: String,
    val refresh: String
)
