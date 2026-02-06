package com.yapp.ndgl.feature.home.main

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.yapp.ndgl.core.base.UiIntent
import com.yapp.ndgl.core.base.UiSideEffect
import com.yapp.ndgl.core.base.UiState
import com.yapp.ndgl.data.travel.model.TravelSummary
import java.time.LocalDate

@Stable
data class HomeState(
    val userName: String = "",
    val myTravel: MyTravel = MyTravel.None,
    val popularTravelSelectedTabIndex: Int = 0,
    val popularTravelTabs: List<PopularTravelTab> = emptyList(),
    val popularTravelsByTab: Map<String, List<TravelSummary>> = emptyMap(),
    val recommendedContents: List<TravelSummary> = emptyList(),
) : UiState {
    @Stable
    sealed interface MyTravel {
        @Immutable
        data object None : MyTravel

        @Immutable
        data class Upcoming(
            val title: String,
            val imageUrl: String,
            val dDay: Int,
            val startDate: LocalDate,
            val endDate: LocalDate,
        ) : MyTravel

        @Immutable
        data class InProgress(
            val title: String,
            val dayCount: Int,
            val startDate: LocalDate,
            val endDate: LocalDate,
            val currentPlace: TravelPlace,
        ) : MyTravel
    }

    data class TravelPlace(
        val category: String,
        val estimatedTime: String,
        val name: String,
        val thumbnailUrl: String,
    )

    data class PopularTravelTab(
        val tag: String,
        val name: String,
        @DrawableRes val icon: Int? = null,
    )
}

sealed interface HomeIntent : UiIntent {
    data class SelectPopularTravelTab(val index: Int) : HomeIntent
}

sealed interface HomeSideEffect : UiSideEffect
