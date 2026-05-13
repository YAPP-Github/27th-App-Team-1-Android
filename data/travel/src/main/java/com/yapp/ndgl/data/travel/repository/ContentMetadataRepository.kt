package com.yapp.ndgl.data.travel.repository

import com.yapp.ndgl.data.travel.api.YoutubeOembedApi
import com.yapp.ndgl.data.travel.model.YoutubeOembedResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContentMetadataRepository @Inject constructor(
    private val api: YoutubeOembedApi,
) {
    suspend fun getMetadata(videoUrl: String): YoutubeOembedResponse =
        api.getMetadata(url = videoUrl)
}
