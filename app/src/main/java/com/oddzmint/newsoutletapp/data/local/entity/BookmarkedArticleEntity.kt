package com.oddzmint.newsoutletapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bookmarked_article")
data class BookmarkedArticleEntity(
    @PrimaryKey val link: String,
    val title: String,
    val description: String,
    val imageUrl: String?,
    val sourceName: String,
    val pubDate: String?
)