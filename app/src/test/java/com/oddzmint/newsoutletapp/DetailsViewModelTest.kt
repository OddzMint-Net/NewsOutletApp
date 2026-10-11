package com.oddzmint.newsoutletapp

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.useCase.GetBookmarkStatus
import com.oddzmint.newsoutletapp.domain.useCase.ToggleBookmark
import com.oddzmint.newsoutletapp.presentation.common.components.SelectedArticleHolder
import com.oddzmint.newsoutletapp.presentation.viewmodel.DetailsViewModel
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

class DetailsViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private val getBookmarkStatus: GetBookmarkStatus = mockk()
    private val toggleBookmark: ToggleBookmark = mockk(relaxed = true)
    private lateinit var holder: SelectedArticleHolder

    private val sampleArticle = Article(
        title = "OddzMint puts mega rocket into orbit",
        description = "A major milestone",
        link = "https://oddzmint.net",
        imageUrl = null,
        sourceName = "Times",
        pubDate = "2030-01-01"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        holder = SelectedArticleHolder()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = DetailsViewModel(holder, getBookmarkStatus, toggleBookmark)


    @Test
    fun `article is exposed when the holder contains one`() {
        holder.article = sampleArticle

        val viewModel = createViewModel()
        assertThat(viewModel.article).isEqualTo(sampleArticle)
    }

    @Test
    fun `article is null when the holder is empty`() {
        val viewModel = createViewModel()
        assertThat(viewModel.article).isNull()
    }

    @Test
    fun `toggleBookmark bookmarks the article when it is not bookmarked`() = runTest {
        val viewModel = createViewModel()
        viewModel.toggleBookmark(sampleArticle, currentlyBookmarked = true)
        testDispatcher.scheduler.advanceUntilIdle()
        coVerify(exactly = 1) { toggleBookmark.remove(sampleArticle) }
        coVerify(exactly = 0) { toggleBookmark.bookmark(any()) }
    }

    @Test
    fun `isBookmarked returns what the use case reports`() = runTest {
        every { getBookmarkStatus(sampleArticle.link) } returns flowOf(true)
        val viewModel = createViewModel()
        viewModel.isBookmarked(sampleArticle.link).test {
            assertThat(awaitItem()).isTrue()
            awaitComplete()
        }
    }
}