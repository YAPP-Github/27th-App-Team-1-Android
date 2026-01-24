package com.yapp.ndgl.feature.home.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.designSystem.NDGLChipTab
import com.yapp.ndgl.core.ui.designSystem.NDGLChipTabAttr
import com.yapp.ndgl.core.ui.designSystem.NDGLNavigationBar
import com.yapp.ndgl.core.ui.designSystem.NDGLNavigationBarAttr
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.time.LocalDate

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()
    HomeScreen(
        state = state,
        onTabSelected = { viewModel.onIntent(HomeIntent.SelectPopularTravelTab(it)) },
    )
}

@Composable
private fun HomeScreen(
    state: HomeState = HomeState(),
    onTabSelected: (Int) -> Unit = {},
) {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        NDGLNavigationBar(
            textAlignType = NDGLNavigationBarAttr.TextAlignType.START,
            modifier = Modifier.fillMaxWidth(),
            headline = "나도갈래",
            trailingIcons = persistentListOf(
                R.drawable.ic_28_search,
            ),
            onTrailingIconClick = {},
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            state.myTravel?.let { myTravel ->
                item {
                    MyTravelCard(
                        myTravel = myTravel,
                        modifier = Modifier.padding(horizontal = 24.dp),
                    )
                }
            }

            item {
                PopularTravelSection(
                    tabs = state.popularTravelTabs,
                    selectedTabIndex = state.popularTravelSelectedTabIndex,
                    travels = state.popularTravels,
                    onTabSelected = onTabSelected,
                )
            }

            if (state.recommendedContents.isNotEmpty()) {
                item {
                    RecommendedContentSection(
                        contents = state.recommendedContents,
                    )
                }
            }
        }
    }
}

@Composable
private fun MyTravelCard(
    myTravel: HomeState.MyTravel,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(NDGLTheme.colors.secondary50)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                text = "${myTravel.title} ${myTravel.dayCount}일차 입니다!",
                style = NDGLTheme.typography.subtitleMdSemiBold,
                color = NDGLTheme.colors.secondary700,
            )
            Text(
                text = "${myTravel.startDate.monthValue}월 ${myTravel.startDate.dayOfMonth}일~" +
                    "${myTravel.endDate.monthValue}월${myTravel.endDate.dayOfMonth}일",
                style = NDGLTheme.typography.bodyMdRegular,
                color = NDGLTheme.colors.secondary500,
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(NDGLTheme.colors.white)
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_14_car),
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = NDGLTheme.colors.secondary400,
                    )
                    Text(
                        text = myTravel.currentPlace.category,
                        style = NDGLTheme.typography.bodySmMedium,
                        color = NDGLTheme.colors.secondary400,
                    )
                    Text(
                        text = "•",
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.secondary400,
                    )
                    Text(
                        text = myTravel.currentPlace.estimatedTime,
                        style = NDGLTheme.typography.bodySmMedium,
                        color = NDGLTheme.colors.secondary400,
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = myTravel.currentPlace.name,
                        style = NDGLTheme.typography.bodyLgSemiBold,
                        color = NDGLTheme.colors.secondary900,
                    )
                    Text(
                        text = myTravel.currentPlace.description,
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.secondary500,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(NDGLTheme.colors.secondary100),
            )
        }
    }
}

@Composable
private fun PopularTravelSection(
    tabs: List<HomeState.PopularTravelTab>,
    selectedTabIndex: Int,
    travels: List<HomeState.PopularTravel>,
    onTabSelected: (Int) -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "인기 여행 따라가기",
            style = NDGLTheme.typography.subtitleLgSemiBold,
            color = NDGLTheme.colors.secondary900,
            modifier = Modifier.padding(horizontal = 24.dp),
        )

        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            NDGLChipTab(
                tabs = tabs.map { tab ->
                    NDGLChipTabAttr.Tab(
                        tag = tab.tag,
                        name = tab.name,
                        icon = tab.icon,
                    )
                }.toPersistentList(),
                selectedIndex = selectedTabIndex,
                onTabSelected = onTabSelected,
                modifier = Modifier.padding(start = 24.dp),
            )

            Column(
                modifier = Modifier.padding(horizontal = 24.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                travels.forEach { travel ->
                    PopularTravelItem(travel = travel)
                }
            }
        }

        Box(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .fillMaxWidth()
                .height(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(NDGLTheme.colors.white)
                .border(
                    width = 1.dp,
                    color = NDGLTheme.colors.secondary200,
                    shape = RoundedCornerShape(8.dp),
                )
                .clickable { },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "여행 따라가기 더보기",
                style = NDGLTheme.typography.bodyMdSemiBold,
                color = NDGLTheme.colors.secondary600,
            )
        }
    }
}

@Composable
private fun PopularTravelItem(
    travel: HomeState.PopularTravel,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .width(140.dp)
                .height(88.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(NDGLTheme.colors.secondary200),
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Box(
                modifier = Modifier
                    .width(18.dp)
                    .height(13.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(NDGLTheme.colors.secondary200),
            )

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = travel.title,
                    style = NDGLTheme.typography.bodyLgMedium,
                    color = NDGLTheme.colors.secondary900,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = travel.country,
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = Color(0xFF7E7E7E),
                    )
                    Text(
                        text = "•",
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.secondary400,
                    )
                    Text(
                        text = travel.duration,
                        style = NDGLTheme.typography.bodyMdRegular,
                        color = NDGLTheme.colors.secondary400,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecommendedContentSection(
    contents: List<HomeState.RecommendedContent>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = "나혜주님께 추천하는\n따라가기 여행 콘텐츠에요!",
            style = NDGLTheme.typography.subtitleLgSemiBold,
            color = NDGLTheme.colors.secondary900,
            modifier = Modifier.padding(horizontal = 24.dp),
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(contents, key = { it.id }) { content ->
                RecommendedContentCard(content = content)
            }
        }
    }
}

@Composable
private fun RecommendedContentCard(
    content: HomeState.RecommendedContent,
) {
    Column(
        modifier = Modifier
            .width(240.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp)
                .background(NDGLTheme.colors.secondary200),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NDGLTheme.colors.white)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color(0xFFE9F8ED))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFA3E4B3),
                        shape = RoundedCornerShape(4.dp),
                    )
                    .padding(horizontal = 10.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .width(14.dp)
                        .height(10.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(NDGLTheme.colors.secondary200),
                )
                Text(
                    text = content.countryTag,
                    style = NDGLTheme.typography.bodySmSemiBold,
                    color = Color(0xFF15C32D),
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = content.title,
                    style = NDGLTheme.typography.bodyLgSemiBold,
                    color = NDGLTheme.colors.secondary700,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    content.creatorIcon?.let { icon ->
                        Icon(
                            imageVector = ImageVector.vectorResource(icon),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp),
                            tint = NDGLTheme.colors.secondary400,
                        )
                    }
                    Text(
                        text = content.creatorName,
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.secondary400,
                    )
                    Text(
                        text = "•",
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.secondary400,
                    )
                    Text(
                        text = content.duration,
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.secondary400,
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    NDGLTheme {
        HomeScreen(
            state = HomeState(
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
                    HomeState.PopularTravel("1", "", "", "곽준빈의 신혼여행", "파리", "7박 9일"),
                    HomeState.PopularTravel("2", "", "", "스위스 여행", "스위스", "5박 6일"),
                    HomeState.PopularTravel("3", "", "", "충격적인 북유럽 물가", "덴마크", "4박 6일"),
                ),
                recommendedContents = listOf(
                    HomeState.RecommendedContent("1", "", "인도", "생각보다 깨끗한 인도 경험하기", "빠니보틀", R.drawable.ic_20_video, "5박 6일"),
                    HomeState.RecommendedContent("2", "", "파리", "생각보다 깨끗한 인도 경험하기", "빠니보틀", R.drawable.ic_20_video, "5박 6일"),
                ),
            ),
        )
    }
}
