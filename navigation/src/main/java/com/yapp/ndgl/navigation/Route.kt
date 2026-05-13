package com.yapp.ndgl.navigation

import androidx.navigation3.runtime.NavKey
import com.yapp.ndgl.navigation.model.RouteAlternativePlace
import com.yapp.ndgl.navigation.model.RouteTipContent
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Splash : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object TemplateSearch : Route

    @Serializable
    data object PopularTravelList : Route

    @Serializable
    data object Settings : Route

    @Serializable
    data object Travel : Route

    @Serializable
    data class FollowTravel(val travelId: Long, val days: Int) : Route

    @Serializable
    data class DatePicker(val templateId: Long, val tripDays: Int) : Route

    @Serializable
    data class TravelDetail(val travelId: Long, val days: Int) : Route

    @Serializable
    data class PlaceDetail(
        val googlePlaceId: String,
        val tipContent: RouteTipContent? = null,
        val alternativePlaces: List<RouteAlternativePlace>? = null,
        val travelId: Long? = null,
        val day: Int? = null,
        val itineraryId: Long? = null,
    ) : Route

    @Serializable
    data class FollowPlaceDetail(
        val googlePlaceId: String,
        val tipContent: RouteTipContent? = null,
        val alternativePlaces: List<RouteAlternativePlace> = emptyList(),
    ) : Route

    @Serializable
    data class AddItinerary(
        val travelId: Long,
        val day: Int,
        val countryCode: String,
        val representativeLatitude: Double,
        val representativeLongitude: Double,
    ) : Route

    @Serializable
    data class AddPlace(val googlePlaceId: String) : Route

    @Serializable
    data object TravelHelper : Route

    @Serializable
    data object ContentRecommendation : Route
}
