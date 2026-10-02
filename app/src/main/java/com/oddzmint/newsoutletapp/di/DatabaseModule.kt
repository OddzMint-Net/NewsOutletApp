package com.oddzmint.newsoutletapp.di

import android.content.Context
import androidx.room.Room
import com.oddzmint.newsoutletapp.data.BookmarkResponsibleImpl
import com.oddzmint.newsoutletapp.data.local.NewsDatabase
import com.oddzmint.newsoutletapp.data.local.dao.BookmarkDao
import com.oddzmint.newsoutletapp.domain.repository.BookmarkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideNewsDatabase(@ApplicationContext context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            "news_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(database: NewsDatabase): BookmarkDao {
        return database.bookmarkDao()
    }

    @Provides
    @Singleton
    fun providedBookmarkRepository(dao: BookmarkDao): BookmarkRepository {
        return BookmarkResponsibleImpl(dao)
    }
}