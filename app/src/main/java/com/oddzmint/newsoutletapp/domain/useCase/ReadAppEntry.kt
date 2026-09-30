package com.oddzmint.newsoutletapp.domain.useCase

import com.oddzmint.newsoutletapp.domain.repository.AppEntryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReadAppEntry @Inject constructor(
    private val repository: AppEntryRepository
) {
    operator fun invoke(): Flow<Boolean> {
        return repository.readAppEntry()
    }
}