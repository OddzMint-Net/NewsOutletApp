package com.oddzmint.newsoutletapp

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.oddzmint.newsoutletapp.data.AppEntryRepositoryImpl
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File

@RunWith(AndroidJUnit4::class)
class AppEntryRepositoryImplTest {

    private val testContext = ApplicationProvider.getApplicationContext<Context>()
    private val testFile = File(testContext.filesDir, "test_app_prefs.preferences_pb")

    private val testDataStore: DataStore<Preferences> = PreferenceDataStoreFactory.create(
        produceFile = { testFile }
    )

    private val repository = AppEntryRepositoryImpl(testDataStore)

    @Test
    fun `readAppEntry returns false before anything is saved`() = runTest {
        val result = repository.readAppEntry().first()
        assertThat(result).isFalse()
    }

    @Test
    fun `saveAppEntry then readAppEntry returns true`() = runTest {
        repository.saveAppEntry()
        val result = repository.readAppEntry().first()
        assertThat(result).isTrue()
    }
}