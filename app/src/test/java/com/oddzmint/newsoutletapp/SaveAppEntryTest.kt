package com.oddzmint.newsoutletapp

import com.oddzmint.newsoutletapp.domain.repository.AppEntryRepository
import com.oddzmint.newsoutletapp.domain.useCase.SaveAppEntry
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveAppEntryTest {
    private val repository: AppEntryRepository = mockk(relaxed = true)
    private val saveAppEntry = SaveAppEntry(repository)

    @Test
    fun `invoke calls repository saveAppEntry`() = runTest {
        saveAppEntry()
        coVerify(exactly = 1) { repository.saveAppEntry() }
    }
}