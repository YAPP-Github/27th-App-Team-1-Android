package com.yapp.ndgl.feature.travel

import com.yapp.ndgl.core.base.UiIntent
import com.yapp.ndgl.core.base.UiSideEffect
import com.yapp.ndgl.core.base.UiState

data class TravelState(
    val displayText: String = "초기 상태",
) : UiState

sealed interface TravelIntent : UiIntent {
    data class ClickTravel(val travelId: Int) : TravelIntent
}

sealed interface TravelSideEffect : UiSideEffect {
    data class NavigateToFollowTravel(val travelId: Int) : TravelSideEffect
}
