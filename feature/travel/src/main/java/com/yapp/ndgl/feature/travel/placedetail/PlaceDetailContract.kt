package com.yapp.ndgl.feature.travel.placedetail

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.yapp.ndgl.core.base.UiIntent
import com.yapp.ndgl.core.base.UiSideEffect
import com.yapp.ndgl.core.base.UiState
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.util.formatDecimal
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours

data class PlaceDetailState(
    val placeInfo: PlaceInfo = PlaceInfo(),
    val selectedTab: PlaceDetailTab = PlaceDetailTab.INFO,
    val photos: List<PlacePhoto> = emptyList(),
    val selectedAlternativePlace: AlternativePlace? = null,
    val showChangeModal: Boolean = false,
) : UiState

data class PlaceInfo(
    // TODO("priceLevel, category, tips 추후 변경)
    val id: String = "",
    val name: String = "",
    val category: String = "",
    val address: String = "",
    val phoneNumber: String = "",
    val openingHours: String = "",
    val googleMapsUri: String = "",
    val websiteUrl: String = "",
    val userRatingCount: Int = 0,
    val rating: Double = 0.0,
    val estimatedDuration: Duration = 0.hours,
    val thumbnail: String = "",
    val tips: List<String> = emptyList(),
    val alternativePlaces: List<AlternativePlace> = emptyList(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val creatorName: String = "",
) {
    val formattedRatingCount: String
        get() = userRatingCount.formatDecimal()
}

enum class PlaceDetailTab(@StringRes val titleRes: Int) {
    INFO(R.string.place_detail_tab_info),
    PHOTO(R.string.place_detail_tab_photo),
}

data class PlacePhoto(
    val url: String,
    val width: Int,
    val height: Int,
) {
    val aspectRatio: Float
        get() = width.toFloat() / height.toFloat()
}

data class AlternativePlace(
    val id: Int,
    val name: String,
    val thumbnail: String,
    val placeType: PlaceType,
)

enum class PlaceType(@StringRes val labelRes: Int, @DrawableRes val iconRes: Int) {
    ACCOMMODATION(R.string.place_type_accommodation, R.drawable.ic_14_home),
    RESTAURANT(R.string.place_type_restaurant, R.drawable.ic_14_restaurant),
    ATTRACTION(R.string.place_type_attraction, R.drawable.ic_14_flag),
    CAFE(R.string.place_type_cafe, R.drawable.ic_14_coffee),
    TRANSPORT(R.string.place_type_transport, R.drawable.ic_14_car),
}

sealed interface PlaceDetailIntent : UiIntent {
    data class SelectTab(val tab: PlaceDetailTab) : PlaceDetailIntent
    data class ClickChangePlace(val alternativePlace: AlternativePlace) : PlaceDetailIntent
    data object ConfirmChangePlace : PlaceDetailIntent
    data object DismissChangeModal : PlaceDetailIntent
    data object ClickAddress : PlaceDetailIntent
    data object ClickMenu : PlaceDetailIntent
    data object ClickAddScheduleButton : PlaceDetailIntent
}

sealed interface PlaceDetailSideEffect : UiSideEffect {
    data class NavigateToBrowser(val url: String) : PlaceDetailSideEffect
}
