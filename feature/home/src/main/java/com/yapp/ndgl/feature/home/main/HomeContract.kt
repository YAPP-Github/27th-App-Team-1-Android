package com.yapp.ndgl.feature.home.main

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Stable
import com.yapp.ui.base.UiIntent
import com.yapp.ui.base.UiSideEffect
import com.yapp.ui.base.UiState
import java.time.LocalDate

@Stable
data class HomeState(
    val myTravel: MyTravel? = null,
    val popularTravelSelectedTabIndex: Int = 0,
    val popularTravelTabs: List<PopularTravelTab> = emptyList(),
    val popularTravels: List<PopularTravel> = emptyList(),
    val recommendedContents: List<RecommendedContent> = emptyList(),
) : UiState {
    data class MyTravel(
        val title: String,
        val dayCount: Int,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val currentPlace: TravelPlace,
    )

    data class TravelPlace(
        val category: String,
        val estimatedTime: String,
        val name: String,
        val description: String,
        val thumbnailUrl: String,
    )

    data class PopularTravelTab(
        val tag: String,
        val name: String,
        @DrawableRes val icon: Int? = null,
    )

    data class PopularTravel(
        val id: String,
        val thumbnailUrl: String,
        val flagUrl: String,
        val title: String,
        val country: String,
        val duration: String,
    )

    data class RecommendedContent(
        val id: String,
        val thumbnailUrl: String,
        val countryTag: String,
        val title: String,
        val creatorName: String,
        val creatorIcon: Int? = null,
        val duration: String,
    )
}

sealed interface HomeIntent : UiIntent {
    data class SelectPopularTravelTab(val index: Int) : HomeIntent
}

sealed interface HomeSideEffect : UiSideEffect
