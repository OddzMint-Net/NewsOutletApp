package com.oddzmint.newsoutletapp

import app.cash.turbine.test
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.repository.BookmarkRepository
import com.oddzmint.newsoutletapp.domain.useCase.ToggleBookmark
import com.oddzmint.newsoutletapp.presentation.viewmodel.BookmarkViewModel
import io.mockk.coVerify
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

class BookmarkViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val bookmarkRepository: BookmarkRepository = mockk()
    private val toggleBookmark: ToggleBookmark = mockk(relaxed = true)

    private val sampleArticle = Article(
        title = "Odwa Starts a Billion dollar company",
        description = "A major milestone..",
        link = "https:oddzmint.net",
        imageUrl = null,
        sourceName = "Times magazine",
        pubDate = "2030-10-07  14:26:03"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `bookmarks reflects what the repository returns`() = runTest {
        every { bookmarkRepository.getAllBookmarks() } returns flowOf (listOf(sampleArticle))

        val viewModel = BookmarkViewModel(bookmarkRepository,toggleBookmark)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.bookmarks.test {
            val initial = awaitItem()
            assert(initial.isEmpty())

            testDispatcher.scheduler.advanceUntilIdle()
            val updated = awaitItem()
            assert(updated== listOf(sampleArticle))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `removeBookmark calls toggleBookmark remove`() = runTest {
        every { bookmarkRepository.getAllBookmarks() } returns flowOf(emptyList())
        val viewModel = BookmarkViewModel(bookmarkRepository,toggleBookmark)
        viewModel.removeBookmark(sampleArticle)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { toggleBookmark.remove(sampleArticle)  }
    }
}