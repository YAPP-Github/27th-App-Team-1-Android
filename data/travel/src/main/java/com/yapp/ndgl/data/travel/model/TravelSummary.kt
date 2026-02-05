package com.yapp.ndgl.data.travel.model

data class TravelSummary(
    val travelId: String,
    val country: String,
    val city: String,
    val nights: Int,
    val days: Int,
    val youtube: YoutubeInfo,
) {
    data class YoutubeInfo(
        val title: String,
        val youtuber: String,
        val thumbnail: String,
    )
}
