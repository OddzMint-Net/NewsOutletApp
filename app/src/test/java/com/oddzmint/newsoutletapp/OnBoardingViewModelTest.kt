package com.oddzmint.newsoutletapp

import com.oddzmint.newsoutletapp.domain.useCase.AppEntryUseCases
import com.oddzmint.newsoutletapp.domain.useCase.ReadAppEntry
import com.oddzmint.newsoutletapp.domain.useCase.SaveAppEntry
import com.oddzmint.newsoutletapp.presentation.onboarding.OnBoardingEvent
import com.oddzmint.newsoutletapp.presentation.viewmodel.OnBoardingViewModel
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class OnBoardingViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val saveAppEntry: SaveAppEntry = mockk(relaxed = true)
    private val readAppEntry: ReadAppEntry = mockk(relaxed = true)
    private lateinit var appEntryUseCases: AppEntryUseCases
    private lateinit var viewModel: OnBoardingViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        appEntryUseCases = AppEntryUseCases(
            saveAppEntry = saveAppEntry,
            readAppEntry = readAppEntry
        )
        viewModel = OnBoardingViewModel(appEntryUseCases)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `SaveAppEntry event calls saveAppEntry use case`() = runTest {
        viewModel.onEvent(OnBoardingEvent.SaveAppEntry)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) { saveAppEntry() }
    }
}