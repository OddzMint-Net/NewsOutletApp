package com.oddzmint.newsoutletapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.oddzmint.newsoutletapp.data.local.dao.BookmarkDao
import com.oddzmint.newsoutletapp.data.local.entity.BookmarkedArticleEntity

@Database(
    entities = [BookmarkedArticleEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {
    abstract fun bookmarkDao(): BookmarkDao
}