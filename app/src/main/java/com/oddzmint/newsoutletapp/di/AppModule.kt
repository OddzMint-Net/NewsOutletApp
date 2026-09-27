package com.oddzmint.newsoutletapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.oddzmint.newsoutletapp.data.AppEntryRepositoryImpl
import com.oddzmint.newsoutletapp.domain.AppEntryRepository
import com.oddzmint.newsoutletapp.domain.useCase.AppEntryUseCases
import com.oddzmint.newsoutletapp.domain.useCase.ReadAppEntry
import com.oddzmint.newsoutletapp.domain.useCase.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_entry_prefs")

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.dataStore
    }

    @Provides
    @Singleton
    fun provideAppEntryRepository(
        dataStore: DataStore<Preferences>
    ): AppEntryRepository {
        return AppEntryRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        repository: AppEntryRepository
    ): AppEntryUseCases {
        return AppEntryUseCases(
            saveAppEntry = SaveAppEntry(repository),
            readAppEntry = ReadAppEntry(repository)
        )
    }
}