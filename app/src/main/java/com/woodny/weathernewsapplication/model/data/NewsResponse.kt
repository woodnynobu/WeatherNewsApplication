package com.woodny.weathernewsapplication.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(
    val _type: String,
    val webSearchUrl: String,
    val value: List<Article>?,
)

data class Article(
    val name: String,
    val url: String,
    val image: ImageDetail?,
    val description: String,
    val provider: Array<ProviderDetail>,
    val datePublished: String,
)

data class ImageDetail(
    val thumbnail: ThumbnailDetail,
    val isLicensed: Boolean,
)

data class ThumbnailDetail(
    val contentUrl: String,
    val width: Int,
    val height: Int,
)

data class ProviderDetail(
    val _type: String,
    val name: String,
    val image: ImageProvider?,
)

data class ImageProvider(
    val thumbnail: ThumbnailProvider
)

data class ThumbnailProvider(
    val contentUrl: String,
)





//data class NewsResponse(
//    val status: String,
//    val totalResults: Int,
//    val articles: List<Article>?,
//)

//data class Article(
//    val source: Source,
//    val author: String,
//    val title: String,
//    val description: String?,
//    val url: String,
//    val urlToImage: String?,
//    val publishedAt: String,
//    val content: String?
//)

data class Source(
    val id: String,
    val name: String,
)