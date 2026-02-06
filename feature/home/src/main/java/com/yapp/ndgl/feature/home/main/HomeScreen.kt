package com.yapp.ndgl.feature.home.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBar
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBarAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationIcon
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.data.travel.model.TravelSummary
import java.time.LocalDate

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToFollowTravel: () -> Unit,
    innerPadding: PaddingValues,
) {
    val state by viewModel.collectAsState()
    HomeScreen(
        state = state,
        innerPadding = innerPadding,
        navigateToFollowTravel = navigateToFollowTravel,
        onTabSelected = { index ->
            viewModel.onIntent(HomeIntent.SelectPopularTravelTab(index))
        },
    )
}

@Composable
private fun HomeScreen(
    state: HomeState = HomeState(),
    innerPadding: PaddingValues = PaddingValues(),
    navigateToFollowTravel: () -> Unit,
    onTabSelected: (Int) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.padding(innerPadding),
        topBar = {
            NDGLNavigationBar(
                textAlignType = NDGLNavigationBarAttr.TextAlignType.START,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = NDGLTheme.colors.white),
                trailingContents = {
                    NDGLNavigationIcon(
                        icon = R.drawable.ic_28_search,
                        onClick = { /* FIXME: 홈 검색 */ },
                    )
                    NDGLNavigationIcon(
                        icon = R.drawable.ic_28_settings,
                        onClick = { /* FIXME: 설정 */ },
                    )
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding() + 20.dp,
                bottom = 80.dp,
            ),
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            item {
                MyTravelCardSection(
                    modifier = Modifier.fillMaxWidth(),
                    myTravel = state.myTravel,
                )
            }

            item {
                if (state.popularTravelsByTab.isNotEmpty()) {
                    PopularTravelSection(
                        tabs = state.popularTravelTabs,
                        selectedTabIndex = state.popularTravelSelectedTabIndex,
                        travelsByTab = state.popularTravelsByTab,
                        onTabSelected = onTabSelected,
                        navigateToFollowTravel = navigateToFollowTravel,
                    )
                }
            }

            if (state.recommendedContents.isNotEmpty()) {
                item {
                    RecommendedContentSection(
                        userName = state.userName,
                        contents = state.recommendedContents,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    val sampleTravels = listOf(
        TravelSummary(
            travelId = "1",
            country = "FR",
            city = "파리",
            nights = 7,
            days = 9,
            youtube = TravelSummary.YoutubeInfo(
                title = "곽준빈의 신혼여행",
                youtuber = "곽튜브",
                thumbnail = "https://picsum.photos/200/300",
            ),
        ),
        TravelSummary(
            travelId = "2",
            country = "CH",
            city = "스위스",
            nights = 5,
            days = 6,
            youtube = TravelSummary.YoutubeInfo(
                title = "스위스 여행",
                youtuber = "빠니보틀",
                thumbnail = "https://picsum.photos/200/300",
            ),
        ),
        TravelSummary(
            travelId = "3",
            country = "DK",
            city = "덴마크",
            nights = 4,
            days = 6,
            youtube = TravelSummary.YoutubeInfo(
                title = "충격적인 북유럽 물가",
                youtuber = "곽튜브",
                thumbnail = "https://picsum.photos/200/300",
            ),
        ),
    )

    NDGLTheme {
        HomeScreen(
            state = HomeState(
                userName = "유저123",
                myTravel = HomeState.MyTravel.InProgress(
                    title = "인도 여행",
                    dayCount = 1,
                    startDate = LocalDate.of(2024, 12, 23),
                    endDate = LocalDate.of(2024, 12, 26),
                    currentPlace = HomeState.TravelPlace(
                        category = "교통수단",
                        estimatedTime = "1시간 체류 예상",
                        name = "인도 국제 공항",
                        thumbnailUrl = "",
                    ),
                ),
                popularTravelTabs = listOf(
                    HomeState.PopularTravelTab(tag = "all", name = "전체"),
                    HomeState.PopularTravelTab(tag = "ppanibottle", name = "빠니보틀", icon = R.drawable.ic_20_video),
                    HomeState.PopularTravelTab(tag = "gwaktube", name = "곽튜브", icon = R.drawable.ic_20_video),
                    HomeState.PopularTravelTab(tag = "kongkong", name = "콩콩팡팡", icon = R.drawable.ic_20_tv),
                ),
                popularTravelsByTab = mapOf(
                    "all" to sampleTravels,
                    "ppanibottle" to sampleTravels.filter { it.youtube.youtuber == "빠니보틀" },
                    "gwaktube" to sampleTravels.filter { it.youtube.youtuber == "곽튜브" },
                ),
                recommendedContents = sampleTravels.take(2),
            ),
            navigateToFollowTravel = {},
        )
    }
}
