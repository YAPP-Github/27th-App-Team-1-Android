package com.yapp.ndgl.feature.travel.travel

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
internal fun TravelRoute(
    navigateToFollowTravel: (Int) -> Unit,
    navigateToTravelDetail: (Int) -> Unit,
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: TravelViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()

    TravelScreen(
        state = state,
        clickTravel = { id -> viewModel.onIntent(TravelIntent.ClickTravel(id)) },
        clickTravelDetail = { id -> viewModel.onIntent(TravelIntent.ClickTravelDetail(id)) },
        innerPadding = innerPadding,
    )

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TravelSideEffect.NavigateToFollowTravel -> navigateToFollowTravel(sideEffect.travelId)
            is TravelSideEffect.NavigateToTravelDetail -> navigateToTravelDetail(sideEffect.travelId)
        }
    }
}

@Composable
private fun TravelScreen(
    state: TravelState = TravelState(),
    clickTravel: (Int) -> Unit = {},
    clickTravelDetail: (Int) -> Unit = {},
    innerPadding: PaddingValues,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(text = "내 여행 화면 제작중입니다. 감사합니다.\n")
        }
        item {
            Button(
                onClick = {
                    clickTravel(123)
                },
            ) {
                Text(text = "여행 콘텐츠 따라가기")
            }
        }
        item {
            Button(
                onClick = {
                    clickTravelDetail(456)
                },
            ) {
                Text(text = "내 여행 보러가기")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelScreenPreview() {
    TravelScreen(innerPadding = PaddingValues())
}
