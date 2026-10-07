package com.oddzmint.newsoutletapp

import com.google.common.truth.Truth.assertThat
import com.oddzmint.newsoutletapp.domain.useCase.AppEntryUseCases
import com.oddzmint.newsoutletapp.domain.useCase.ReadAppEntry
import com.oddzmint.newsoutletapp.domain.useCase.SaveAppEntry
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.Route
import com.oddzmint.newsoutletapp.presentation.viewmodel.MainViewModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val saveAppEntry: SaveAppEntry = mockk(relaxed = true)
    private val readAppEntry: ReadAppEntry = mockk()

    @Before
    fun setup(){
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when onboarding is completed, startDestination is NewsNavigation`() = runTest {
        every { readAppEntry() } returns flowOf(true)
        val useCases = AppEntryUseCases(saveAppEntry,readAppEntry)

        val viewModel = MainViewModel(useCases)
        testDispatcher.scheduler.advanceUntilIdle()
        assertThat(viewModel.startDestination).isEqualTo(Route.NewsNavigation.route)
    }

    @Test
    fun `when onboarding is not completed, startDestination is AppStartNavigation`() = runTest {
        every { readAppEntry() } returns flowOf(false)
        val useCases = AppEntryUseCases(saveAppEntry,readAppEntry)

        val viewModel = MainViewModel(useCases)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.startDestination).isEqualTo(Route.AppStartNavigation.route)
    }

    @Test
    fun `splashCondition becomes false once startDestination is determined`() = runTest {
        every { readAppEntry() } returns flowOf(true)
        val useCases = AppEntryUseCases(saveAppEntry,readAppEntry)

        val viewModel = MainViewModel(useCases)
        testDispatcher.scheduler.advanceUntilIdle()

        assertThat(viewModel.splashCondition).isFalse()
    }
}