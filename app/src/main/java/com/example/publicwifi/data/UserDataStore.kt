package com.example.publicwifi.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

enum class UserDataStoreKey {
    USER_ID_KEY,
    USER_PHONE_NUMBER
}

class UserDataStore(
    private val context: Context
) {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("userData")
        val USER_ID_KEY = stringPreferencesKey("USER_ID")
        val USER_PHONE_NUMBER_KEY = stringPreferencesKey("USER_PHONE_NUMBER")
    }

    suspend fun saveUserData(userId: String, phoneNumber: String) {
        context.dataStore.edit { preferences ->
            preferences[USER_ID_KEY] = userId
            preferences[USER_PHONE_NUMBER_KEY] = phoneNumber
        }
    }

    fun findUserInfo(userInfoType: UserDataStoreKey): Flow<String?> {
        val data = context.dataStore.data
            .map { preferences ->
                when (userInfoType) {
                    UserDataStoreKey.USER_ID_KEY -> preferences[USER_ID_KEY]
                    UserDataStoreKey.USER_PHONE_NUMBER -> preferences[USER_PHONE_NUMBER_KEY]
                }
            }
        return data
    }

    suspend fun removeUserData(){
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}