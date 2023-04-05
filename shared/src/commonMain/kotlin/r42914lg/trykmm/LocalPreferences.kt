package r42914lg.trykmm

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LocalPreferences(
    private val dataStore: DataStore<Preferences>,
    private val coroutineScope: CoroutineScope
) {

    fun saveToken(token: String) {
        coroutineScope.launch {
            dataStore.edit {
                it[KEY_FIREBASE] = token
            }
        }
    }

    fun getToken(): Flow<String> = dataStore.data.map {
        it[KEY_FIREBASE] ?: ""
    }

    fun saveJwtToken(token: String) {
        coroutineScope.launch {
            dataStore.edit {
                it[KEY_JWT] = token
            }
        }
    }

    fun getJwtToken(): Flow<String> = dataStore.data.map {
        it[KEY_JWT] ?: ""
    }

    companion object {
        val KEY_FIREBASE = stringPreferencesKey("firebase_token")
        val KEY_JWT = stringPreferencesKey("jwt_token")
    }
}