package com.yapp.ndgl.feature.travel.followtravel

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import com.yapp.ndgl.core.base.UiIntent
import com.yapp.ndgl.core.base.UiSideEffect
import com.yapp.ndgl.core.base.UiState
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme
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

enum class PlaceType(@get:StringRes val labelRes: Int, @get:DrawableRes val iconRes: Int) {
    ACCOMMODATION(R.string.place_type_accommodation, R.drawable.ic_14_home),
    RESTAURANT(R.string.place_type_restaurant, R.drawable.ic_14_restaurant),
    ATTRACTION(R.string.place_type_attraction, R.drawable.ic_14_flag),
    CAFE(R.string.place_type_cafe, R.drawable.ic_14_coffee),
    TRANSPORT(R.string.place_type_transport, R.drawable.ic_14_car),
}

@Composable
fun PlaceType.getColor(): androidx.compose.ui.graphics.Color {
    return when (this) {
        PlaceType.ACCOMMODATION -> NDGLTheme.colors.etcPurple
        PlaceType.RESTAURANT -> NDGLTheme.colors.etcOrange
        PlaceType.ATTRACTION -> NDGLTheme.colors.etcGreen
        PlaceType.CAFE -> NDGLTheme.colors.etcOrange
        PlaceType.TRANSPORT -> NDGLTheme.colors.etcGray
    }
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

enum class TransportType(@StringRes val labelRes: Int, @DrawableRes val iconRes: Int) {
    WALK(R.string.transport_type_walk, R.drawable.ic_20_walk),
    CAR(R.string.transport_type_car, R.drawable.ic_20_car),
    BUS(R.string.transport_type_bus, R.drawable.ic_20_bus),
    TRAIN(R.string.transport_type_train, R.drawable.ic_20_train),
}

sealed interface FollowTravelIntent : UiIntent {
    data class SelectDay(val day: Int) : FollowTravelIntent
    data object ClickFollowTravel : FollowTravelIntent
}

sealed interface FollowTravelSideEffect : UiSideEffect
