package com.yapp.ndgl.feature.travel

import com.yapp.ndgl.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TravelViewModel @Inject constructor() : BaseViewModel<TravelState, TravelIntent, TravelSideEffect>(
    initialState = TravelState(),
) {
    override suspend fun handleIntent(intent: TravelIntent) {
        when (intent) {
            is TravelIntent.ClickTravel -> {
                clickTravel(intent.travelId)
            }
        }
    }

    private fun clickTravel(travelId: Int) {
        reduce { copy(displayText = "클릭된 id: $travelId") }
        postSideEffect(TravelSideEffect.NavigateToFollowTravel(travelId))
    }
}
