package com.oddzmint.newsoutletapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ArticleDto(
    @SerializedName("ai_org")
    val aiOrg: String?,
    @SerializedName("ai_region")
    val aiRegion: String?,
    @SerializedName("ai_summary")
    val aiSummary: String?,
    @SerializedName("ai_tag")
    val aiTag: String?,
    @SerializedName("article_id")
    val articleId: String,
    val category: List<String?>,
    val content: String?,
    val country: List<String>,
    val creator: List<String?>,
    val datatype: String?,
    val description: String?,
    val duplicate: Boolean?,
    @SerializedName("fetched_at")
    val fetchedAt: String?,
    @SerializedName("image_url")
    val imageUrl: String?,
    val keywords: List<String?>,
    val language: String,
    val link: String,
    val pubDate: String?,
    val pubDateTZ: String?,
    val sentiment: String?,
    @SerializedName("sentiment_stats")
    val sentimentStats: String?,
    @SerializedName("source_icon")
    val sourceIcon: String?,
    @SerializedName("source_id")
    val sourceId: String?,
    @SerializedName("source_name")
    val sourceName: String?,
    @SerializedName("source_priority")
    val sourcePriority: Int?,
    @SerializedName("source_url")
    val sourceUrl: String?,
    val title: String?,
    @SerializedName("video_url")
    val video_url: Any?
)