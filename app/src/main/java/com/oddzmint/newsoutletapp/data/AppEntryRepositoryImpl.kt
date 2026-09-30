package com.oddzmint.newsoutletapp.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.oddzmint.newsoutletapp.domain.repository.AppEntryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppEntryRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : AppEntryRepository {

    companion object {
        val APP_ENTRY_KEY = booleanPreferencesKey("app_entry")
    }

    override suspend fun saveAppEntry() {
        dataStore.edit { preferences ->
            preferences[APP_ENTRY_KEY] = true
        }
    }

    override fun readAppEntry(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[APP_ENTRY_KEY] ?: false
        }
    }
}