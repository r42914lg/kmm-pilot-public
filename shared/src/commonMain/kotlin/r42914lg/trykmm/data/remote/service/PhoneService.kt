package r42914lg.trykmm.data.remote.service

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import r42914lg.trykmm.ROOT_API_STAGE
import r42914lg.trykmm.di.SimpleServiceLocator
import r42914lg.trykmm.domain.remote.models.phone.Phone
import r42914lg.trykmm.domain.remote.models.phone.Token
import r42914lg.trykmm.domain.remote.models.sms.JwtToken

interface PhoneService {
    suspend fun sendPhone(phone: Phone): Token
    suspend fun getJwtToken(token: String, code: String) : JwtToken
}

class PhoneServiceImpl : PhoneService {

    private val client = SimpleServiceLocator.getHttpClient()

    override suspend fun sendPhone(phone: Phone): Token =
        client.post(ROOT_API_STAGE + "verify-phone/") {
            url {
                contentType(ContentType.Application.Json)
                setBody(phone)
            }
        }.body()

    override suspend fun getJwtToken(token: String, code: String) : JwtToken =
        client.get(ROOT_API_STAGE + "token/") {
            url {
                header("SMS-TOKEN", token)
                header("SMS-CODE", code)
            }
        }.body()
}

class PhoneServiceTest : PhoneService {

    override suspend fun sendPhone(phone: Phone): Token {
        delay(5000)
        return Token("jfjsgfkjfhskjh")
    }

    override suspend fun getJwtToken(token: String, code: String) : JwtToken {
        delay(5000)
        return JwtToken("kjgkdjhfdksjhfkdjshfkjdshfskdjhfskjdhkjd", "refresh")
    }
}


