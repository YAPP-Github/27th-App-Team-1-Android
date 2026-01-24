package com.yapp.ndgl.feature.home.main

import com.yapp.ndgl.core.ui.R
import com.yapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(
    initialState = HomeState(
        myTravel = HomeState.MyTravel(
            title = "인도 여행",
            dayCount = 1,
            startDate = LocalDate.of(2024, 12, 23),
            endDate = LocalDate.of(2024, 12, 26),
            currentPlace = HomeState.TravelPlace(
                category = "교통수단",
                estimatedTime = "1시간 체류 예상",
                name = "인도 국제 공항",
                description = "장소에 대한 설명 추가",
                thumbnailUrl = "",
            ),
        ),
        popularTravelTabs = listOf(
            HomeState.PopularTravelTab(tag = "all", name = "전체"),
            HomeState.PopularTravelTab(tag = "ppanibottle", name = "빠니보틀", icon = R.drawable.ic_20_video),
            HomeState.PopularTravelTab(tag = "gwaktube", name = "곽튜브", icon = R.drawable.ic_20_video),
            HomeState.PopularTravelTab(tag = "kongkong", name = "콩콩팡팡", icon = R.drawable.ic_20_tv),
        ),
        popularTravels = listOf(
            HomeState.PopularTravel(
                id = "1",
                thumbnailUrl = "",
                flagUrl = "",
                title = "곽준빈의 신혼여행",
                country = "파리",
                duration = "7박 9일",
            ),
            HomeState.PopularTravel(
                id = "2",
                thumbnailUrl = "",
                flagUrl = "",
                title = "스위스 여행",
                country = "스위스",
                duration = "5박 6일",
            ),
            HomeState.PopularTravel(
                id = "3",
                thumbnailUrl = "",
                flagUrl = "",
                title = "충격적인 북유럽 물가",
                country = "덴마크",
                duration = "4박 6일",
            ),
        ),
        recommendedContents = listOf(
            HomeState.RecommendedContent(
                id = "1",
                thumbnailUrl = "",
                countryTag = "인도",
                title = "생각보다 깨끗한 인도 경험하기",
                creatorName = "빠니보틀",
                creatorIcon = R.drawable.ic_20_video,
                duration = "5박 6일",
            ),
            HomeState.RecommendedContent(
                id = "2",
                thumbnailUrl = "",
                countryTag = "파리",
                title = "생각보다 깨끗한 인도 경험하기",
                creatorName = "빠니보틀",
                creatorIcon = R.drawable.ic_20_video,
                duration = "5박 6일",
            ),
        ),
    ),
) {
    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SelectPopularTravelTab -> {
                reduce { copy(popularTravelSelectedTabIndex = intent.index) }
            }
        }
    }
}
