package com.yapp.ndgl.feature.travel.followtravel

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButton
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButtonAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLChipTab
import com.yapp.ndgl.core.ui.designsystem.NDGLChipTabAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBar
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBarAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationIcon
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.ui.util.dropShadow
import com.yapp.ndgl.feature.travel.followtravel.component.ContentCard
import com.yapp.ndgl.feature.travel.followtravel.component.PlaceItem
import com.yapp.ndgl.feature.travel.followtravel.component.TransportSegment
import com.yapp.ndgl.feature.travel.followtravel.component.TravelMap
import kotlinx.collections.immutable.toPersistentList

@Composable
internal fun FollowTravelRoute(
    viewModel: FollowTravelViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
    navigateToDatePicker: (Int) -> Unit,
) {
    val state by viewModel.collectAsState()

    // TODO  viewModel.collectSideEffect { sideEffect -> }

    FollowTravelScreen(
        state = state,
        clickBackButton = navigateBack,
        selectDay = { viewModel.onIntent(FollowTravelIntent.SelectDay(it)) },
        clickFollowTravel = {
            navigateToDatePicker(state.contentInfo.days)
        },
    )
}

@Composable
private fun FollowTravelScreen(
    state: FollowTravelState,
    clickBackButton: () -> Unit,
    selectDay: (Int) -> Unit,
    clickFollowTravel: () -> Unit,
) {
    val tabs = (1..state.contentInfo.days).map { day ->
        NDGLChipTabAttr.Tab(
            tag = "d$day",
            name = stringResource(R.string.day_format, day),
        )
    }.toPersistentList()

    val listState = rememberLazyListState()

    // FIXME("임시 sticky 판단 로직")
    val isHeaderSticky by remember {
        derivedStateOf {
            val firstItem = listState.layoutInfo.visibleItemsInfo.firstOrNull()
            val result = firstItem?.index == 1 && firstItem.offset <= 0
            result
        }
    }

    var columnScrollingEnabled by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding(),
    ) {
        LazyColumn(
            state = listState,
            userScrollEnabled = columnScrollingEnabled,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp))
                        .background(NDGLTheme.colors.black50)
                        .statusBarsPadding(),
                ) {
                    NDGLNavigationBar(
                        textAlignType = NDGLNavigationBarAttr.TextAlignType.START,
                        leadingIcon = R.drawable.ic_28_chevron_left,
                        onLeadingIconClick = clickBackButton,
                        trailingContents = {
                            NDGLNavigationIcon(
                                icon = R.drawable.ic_28_share,
                                onClick = {},
                            )
                        },
                    )
                    ContentCard(contentInfo = state.contentInfo)
                }
            }

            stickyHeader {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(NDGLTheme.colors.white)
                        .then(
                            if (isHeaderSticky) {
                                Modifier
                                    .statusBarsPadding()
                                    .padding(top = 10.dp)
                            } else {
                                Modifier.padding(top = 22.dp)
                            },
                        )
                        .padding(horizontal = 24.dp),
                ) {
                    NDGLChipTab(
                        tabs = tabs,
                        selectedIndex = state.selectedDay - 1,
                        onTabSelected = { index -> selectDay(index + 1) },
                    )
                    Spacer(Modifier.height(16.dp))
                }
            }

            item(key = "map_${state.selectedDay}") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp),
                ) {
                    state.itineraries.getOrNull(state.selectedDay - 1)?.let { itinerary ->
                        val places = itinerary.places
                        if (places.isNotEmpty()) {
                            TravelMap(
                                places = itinerary.places,
                                onScrollEnabledChange = { columnScrollingEnabled = it },
                            )
                        }
                    }
                    Spacer(Modifier.height(23.5.dp))
                }
            }

            val currentItinerary = state.itineraries.getOrNull(state.selectedDay - 1)
            val currentPlaces = currentItinerary?.places.orEmpty()
            val currentTransportSegments = currentItinerary?.transportSegments.orEmpty()

            currentPlaces.forEachIndexed { index, place ->
                item(key = "place_${place.id}") {
                    Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                        PlaceItem(place = place)
                    }
                }

                if (index < currentPlaces.size - 1) {
                    item(key = "transport_${place.id}_$index") {
                        Spacer(Modifier.height(17.5.dp))
                        Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                            currentTransportSegments.getOrNull(index)?.let { segment ->
                                TransportSegment(segment = segment)
                            }
                        }
                        Spacer(Modifier.height(17.5.dp))
                    }
                }
            }

            item {
                Spacer(Modifier.height(60.dp))
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.Black.copy(alpha = 0.06f),
                    blur = 20.dp,
                    offsetY = (-10).dp,
                )
                .align(Alignment.BottomCenter)
                .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                .background(NDGLTheme.colors.white)
                .padding(top = 20.dp, bottom = 16.dp)
                .padding(horizontal = 24.dp),
        ) {
            NDGLCTAButton(
                modifier = Modifier.fillMaxWidth(),
                type = NDGLCTAButtonAttr.Type.PRIMARY,
                size = NDGLCTAButtonAttr.Size.LARGE,
                status = NDGLCTAButtonAttr.Status.ACTIVE,
                label = stringResource(R.string.follow_travel_button),
                onClick = clickFollowTravel,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FollowTravelScreenPreview() {
    FollowTravelScreen(
        state = FollowTravelState(),
        clickBackButton = {},
        selectDay = {},
        clickFollowTravel = {},
    )
}
