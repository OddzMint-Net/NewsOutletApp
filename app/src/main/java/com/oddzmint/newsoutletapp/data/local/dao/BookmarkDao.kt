package com.oddzmint.newsoutletapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.oddzmint.newsoutletapp.data.local.entity.BookmarkedArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: BookmarkedArticleEntity)

    @Delete
    suspend fun delete(article: BookmarkedArticleEntity)

    @Query("SELECT * FROM bookmarked_article ORDER BY pubDate DESC")
    fun getAllBookmarks(): Flow<List<BookmarkedArticleEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM bookmarked_article WHERE link = :link)")
    fun isBookmarked(link: String): Flow<Boolean>

}