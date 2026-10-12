package com.oddzmint.newsoutletapp

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.useCase.GetBookmarkStatus
import com.oddzmint.newsoutletapp.domain.useCase.SearchNews
import com.oddzmint.newsoutletapp.domain.useCase.ToggleBookmark
import com.oddzmint.newsoutletapp.presentation.viewmodel.SearchViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private val searchNews: SearchNews = mockk()
    private val getBookmarkStatus: GetBookmarkStatus = mockk()
    private val toggleBookmark: ToggleBookmark = mockk(relaxed = true)

    private val sampleArticle = Article(
        title = "Loadshedding schedule update",
        description = "New stages announced",
        link = "https://oddzmint",
        imageUrl = null,
        sourceName = "Mnet",
        pubDate = "2030-09-01 17:00:00"
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        every { searchNews(any()) } returns flowOf(PagingData.empty())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = SearchViewModel(searchNews, getBookmarkStatus, toggleBookmark)

    @Test
    fun `query updates immediately without waiting for debounce`() {
        val viewModel = createViewModel()
        viewModel.onQueryChange("load")
        assertThat(viewModel.query).isEqualTo("load")
    }

    @Test
    fun `search is not triggered until typing pauses fpr 500 ms`() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch { viewModel.results.collect { } }
        runCurrent()

        viewModel.onQueryChange("load")
        advanceTimeBy(499)
        verify(exactly = 0) { searchNews(any()) }
        advanceTimeBy(2)
        runCurrent()
        verify(exactly = 1) { searchNews("load") }
    }

    @Test
    fun `blank query never triggers a search`() = runTest {
        val viewModel = createViewModel()
        backgroundScope.launch { viewModel.results.collect { } }
        runCurrent()
        viewModel.onQueryChange(" ")
        advanceTimeBy(1000)
        runCurrent()
        verify(exactly = 0) { searchNews(any()) }
    }

    @Test
    fun `toggleBookmark bookmarks the article when it is not bookmarked`() = runTest {
        val viewModel = createViewModel()
        viewModel.toggleBookmark(sampleArticle, currentlyBookmarked = false)
        coVerify(exactly = 1) { toggleBookmark.bookmark(sampleArticle) }
        coVerify(exactly = 0) { toggleBookmark.remove(any()) }
    }

    @Test
    fun `toggleBookmark removes the article when it is already bookmarked`() = runTest {
        val viewModel = createViewModel()
        viewModel.toggleBookmark(sampleArticle, currentlyBookmarked = true)
        coVerify(exactly = 1) { toggleBookmark.remove(sampleArticle) }
        coVerify(exactly = 0) { toggleBookmark.bookmark(any()) }

    }
}
