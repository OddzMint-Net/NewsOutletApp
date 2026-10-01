package com.oddzmint.newsoutletapp.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import coil3.network.HttpException
import com.oddzmint.newsoutletapp.data.mapper.toArticle
import com.oddzmint.newsoutletapp.domain.model.Article
import okio.IOException

class NewsPagingSource(
    private val api: NewsApi,
    private val country: String,
    private val language: String,
) : PagingSource<String, Article>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Article> {
        return try {
            val response = api.getLatestNews(
                country = country,
                language = language,
                page = params.key
            )
            val articles = response.results
                .filter { it.duplicate != true }
                .map { it.toArticle() }

            LoadResult.Page(
                data = articles,
                prevKey = null,
                nextKey = response.nextPage
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, Article>): String? {
        return null
    }
}