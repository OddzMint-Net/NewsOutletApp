package com.oddzmint.newsoutletapp.domain.useCase

import com.oddzmint.newsoutletapp.domain.AppEntryRepository
import javax.inject.Inject

class SaveAppEntry @Inject constructor(private val repository: AppEntryRepository) {
    suspend operator fun invoke() {
        repository.saveAppEntry()
    }
}