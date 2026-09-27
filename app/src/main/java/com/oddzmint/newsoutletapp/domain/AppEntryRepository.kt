package com.oddzmint.newsoutletapp.domain

import kotlinx.coroutines.flow.Flow

interface AppEntryRepository {
    suspend fun saveAppEntry()
    fun readAppEntry(): Flow<Boolean>
}