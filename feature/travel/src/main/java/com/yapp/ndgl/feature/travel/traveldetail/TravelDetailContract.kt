package com.yapp.ndgl.feature.travel.traveldetail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.yapp.ndgl.core.base.UiIntent
import com.yapp.ndgl.core.base.UiSideEffect
import com.yapp.ndgl.core.base.UiState
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import java.util.Locale.getDefault
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

data class TravelDetailState(
    val contentInfo: ContentInfo = ContentInfo(),
    val selectedDay: Int = 1,
    val itineraries: List<Itinerary> = emptyList(),
    val tempItineraries: List<Itinerary> = emptyList(),
    val isEditMode: Boolean = false,
    val selectedPlaceIds: Set<Int> = emptySet(),
    val showDeleteModal: Boolean = false,
    val showCancelEditModal: Boolean = false,
    val showTimelineBottomSheet: Boolean = false,
    val startTime: Duration = 0.hours,
    val endTime: Duration = 0.hours,
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
    val description: String,
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
fun PlaceType.getColor(): Color {
    return when (this) {
        PlaceType.ACCOMMODATION -> NDGLTheme.colors.etcPurple
        PlaceType.RESTAURANT -> NDGLTheme.colors.etcOrange
        PlaceType.ATTRACTION -> NDGLTheme.colors.etcGreen
        PlaceType.CAFE -> NDGLTheme.colors.etcOrange
        PlaceType.TRANSPORT -> NDGLTheme.colors.etcGray
    }
}

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
                    String.format(getDefault(), "%.1fkm", km)
                }
            }

            else -> "${distance}m"
        }
    }
}

enum class TransportType(@get:StringRes val labelRes: Int, @get:DrawableRes val iconRes: Int) {
    WALK(R.string.transport_type_walk, R.drawable.ic_20_walk),
    CAR(R.string.transport_type_car, R.drawable.ic_20_car),
    BUS(R.string.transport_type_bus, R.drawable.ic_20_bus),
    TRAIN(R.string.transport_type_train, R.drawable.ic_20_train),
}

sealed interface TravelDetailIntent : UiIntent {
    data class SelectDay(val day: Int) : TravelDetailIntent
    data object ClickTimelineAutoSetting : TravelDetailIntent
    data object ClickEditTravel : TravelDetailIntent
    data object ClickAddScheduleButton : TravelDetailIntent
    data class CheckPlaceItem(val placeId: Int) : TravelDetailIntent
    data object CheckSelectAll : TravelDetailIntent
    data object ClickDeleteSelectedPlaces : TravelDetailIntent
    data object ConfirmDeleteSelectedPlaces : TravelDetailIntent
    data object DismissDeleteModal : TravelDetailIntent
    data object ClickBack : TravelDetailIntent
    data object ConfirmCancelEditMode : TravelDetailIntent
    data object DismissCancelEditModal : TravelDetailIntent
    data object LongClickPlaceItem : TravelDetailIntent
    data object DismissTimelineBottomSheet : TravelDetailIntent
    data class ConfirmTimelineSetting(val startTime: Duration) : TravelDetailIntent
    data class ReorderPlaces(val fromIndex: Int, val toIndex: Int) : TravelDetailIntent
    data object ConfirmEditMode : TravelDetailIntent
}

sealed interface TravelDetailSideEffect : UiSideEffect {
    data object NavigateBack : TravelDetailSideEffect
}
