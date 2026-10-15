package com.oddzmint.newsoutletapp

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.useCase.GetBookmarkStatus
import com.oddzmint.newsoutletapp.domain.useCase.GetLatestNews
import com.oddzmint.newsoutletapp.domain.useCase.ToggleBookmark
import com.oddzmint.newsoutletapp.presentation.viewmodel.NewsViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {
    private val testDispatcher = UnconfinedTestDispatcher()
    private val getLatestNews: GetLatestNews = mockk()
    private val getBookmarkStatus: GetBookmarkStatus = mockk()
    private val toggleBookmark: ToggleBookmark = mockk(relaxed = true)

    private val firstArticle = Article(
        title = "Interest rate hike hell",
        description = "How to absorb higher repayments",
        link = "https://oddzmint.net",
        imageUrl = null,
        sourceName = "Times",
        pubDate = "2026-08-33 15:00"
    )
    private val secondArticle = Article(
        title = "Starship reaches orbit",
        description = "A major milestone.",
        link = "https://oddzmint.net",
        imageUrl = null,
        sourceName = "The Times",
        pubDate = "2026-09-28 14:26:03"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = NewsViewModel(getLatestNews, getBookmarkStatus, toggleBookmark)

    @Test
    fun `articles exposes what the use case provides`() = runTest {
        every { getLatestNews() } returns flowOf(
            PagingData.from(listOf(firstArticle, secondArticle))
        )
        val viewModel = createViewModel()
        val result = viewModel.articles.asSnapshot()
        assertThat(result).containsExactly(firstArticle, secondArticle).inOrder()
    }

    @Test
    fun `toggleBookmark bookmarks the article when it is not bookmarked`() = runTest {
        every { getLatestNews() } returns flowOf(PagingData.empty())
        val viewModel = createViewModel()
        viewModel.toggledBookmark(firstArticle, currentlyBookmarked = false)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { toggleBookmark.bookmark(firstArticle) }
        coVerify(exactly = 0) { toggleBookmark.remove(any()) }
    }

    @Test
    fun `toggleBookmark removes the article when it is already bookmarked`() = runTest {
        every { getLatestNews() } returns flowOf(PagingData.empty())
        val viewModel = createViewModel()

        viewModel.toggledBookmark(firstArticle, currentlyBookmarked = true)
        testDispatcher.scheduler.advanceUntilIdle()

        coVerify(exactly = 1) { toggleBookmark.remove(firstArticle) }
        coVerify(exactly = 0) { toggleBookmark.bookmark(any()) }
    }

    @Test
    fun `isBookmarked returns what the use case reports`() = runTest {
        every { getLatestNews() } returns flowOf(PagingData.empty())
        every { getBookmarkStatus(firstArticle.link) } returns flowOf(true)
        val viewModel = createViewModel()
        viewModel.isBookmarked(firstArticle.link).test {
            assertThat(awaitItem()).isTrue()
            awaitComplete()
        }
    }
}