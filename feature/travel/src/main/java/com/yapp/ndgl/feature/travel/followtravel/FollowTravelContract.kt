package com.yapp.ndgl.feature.travel.followtravel

import com.yapp.ndgl.core.base.UiIntent
import com.yapp.ndgl.core.base.UiSideEffect
import com.yapp.ndgl.core.base.UiState
import kotlin.time.Duration

data class FollowTravelState(
    val contentInfo: ContentInfo = ContentInfo(),
    val selectedDay: Int = 1,
    val itineraries: List<Itinerary> = emptyList(),
) : UiState

data class ContentInfo(
    val travelId: String = "",
    val country: String = "",
    val city: String = "",
    val budgetPerPerson: Budget = Budget(0),
    val nights: Int = 0,
    val days: Int = 0,
    val videoInfo: VideoInfo = VideoInfo(),
)

data class VideoInfo(
    val title: String = "",
    val name: String = "",
    val profileImage: String = "",
    val thumbnail: String = "",
    val link: String = "",
    val summary: String = "",
)

data class Budget(
    val amount: Int,
) {
    fun formatString(): String {
        return when {
            amount < 10000 -> "만원"
            amount % 10000 == 0 -> "${amount / 10000}만원"
            amount % 1000 == 0 -> "${amount / 10000}만 ${(amount % 10000) / 1000}천원"
            else -> "${amount}원"
        }
    }
}

data class Itinerary(
    val budget: Budget = Budget(0),
    val places: List<TravelPlace> = emptyList(),
    val transportSegments: List<TransportSegment> = emptyList(),
)

data class TravelPlace(
    val id: Int,
    val day: Int,
    val sequence: Int,
    val estimatedDuration: Duration,
    val googlePlaceId: String,
    val thumbnail: String,
    val latitude: Double,
    val longitude: Double,
    val name: String,
    val regularOpeningHours: String,
    val googleMapsUri: String,
    val placeType: PlaceType,
)

enum class PlaceType(val label: String) {
    ACCOMMODATION("숙소"),
    RESTAURANT("음식점"),
    ATTRACTION("관광명소"),
    CAFE("카페"),
    TRANSPORT("교통수단"),
}

// FIXME(현재 API는 미제작된 클래스)
data class TransportSegment(
    val type: TransportType,
    val duration: Duration,
    val distance: Int,
) {
    fun formatDistance(): String {
        return when {
            distance >= 1000 -> {
                val km = distance / 1000.0
                if (km % 1 == 0.0) {
                    "${km.toInt()}km"
                } else {
                    String.format(java.util.Locale.getDefault(), "%.1fkm", km)
                }
            }
            else -> "${distance}m"
        }
    }
}

enum class TransportType(val label: String) {
    WALK("도보"),
    CAR("자동차"),
    BUS("버스"),
    TRAIN("기차"),
}

sealed interface FollowTravelIntent : UiIntent {
    data class OnDaySelected(val day: Int) : FollowTravelIntent
    data object OnFollowClick : FollowTravelIntent
    data class OnPlaceDescriptionClick(val placeId: Int) : FollowTravelIntent
}

sealed interface FollowTravelSideEffect : UiSideEffect
