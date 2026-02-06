package com.yapp.ndgl.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Route : NavKey {
    @Serializable
    data object Auth : Route

    @Serializable
    data object Home : Route

    @Serializable
    data object Travel : Route

    @Serializable
    data class FollowTravel(val travelId: Int) : Route

    @Serializable
    data class TravelDetail(val travelId: Int) : Route

    @Serializable
    data class PlaceDetail(val placeId: String) : Route

    @Serializable
    data class DatePicker(val tripDays: Int) : Route

    @Serializable
    data object TravelHelper : Route
}
