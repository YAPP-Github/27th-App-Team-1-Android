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
    data object TravelHelper : Route
}
