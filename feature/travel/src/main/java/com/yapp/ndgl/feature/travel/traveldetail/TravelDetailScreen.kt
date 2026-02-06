package com.yapp.ndgl.feature.travel.traveldetail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.designsystem.NDGLBottomSheet
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButton
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButtonAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLChipTab
import com.yapp.ndgl.core.ui.designsystem.NDGLChipTabAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLModal
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBar
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBarAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationIcon
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.ui.util.dropShadow
import com.yapp.ndgl.core.ui.util.launchBrowser
import com.yapp.ndgl.feature.travel.traveldetail.component.ContentCard
import com.yapp.ndgl.feature.travel.traveldetail.component.EditControlBar
import com.yapp.ndgl.feature.travel.traveldetail.component.EditablePlaceItem
import com.yapp.ndgl.feature.travel.traveldetail.component.PlaceBottomSheet
import com.yapp.ndgl.feature.travel.traveldetail.component.PlaceItem
import com.yapp.ndgl.feature.travel.traveldetail.component.TimelineContent
import com.yapp.ndgl.feature.travel.traveldetail.component.TransportSegment
import com.yapp.ndgl.feature.travel.traveldetail.component.TravelDetailToolBar
import com.yapp.ndgl.feature.travel.traveldetail.component.TravelMap
import kotlinx.collections.immutable.persistentListOf
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

@Composable
internal fun TravelDetailRoute(
    viewModel: TravelDetailViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToPlaceDetail: (String) -> Unit,
) {
    val state by viewModel.collectAsState()
    val context = LocalContext.current

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is TravelDetailSideEffect.NavigateBack -> navigateBack()
            is TravelDetailSideEffect.NavigateToPlaceDetail -> {
                navigateToPlaceDetail(sideEffect.placeId)
            }

            is TravelDetailSideEffect.NavigateToBrowser -> {
                context.launchBrowser(sideEffect.url)
            }
        }
    }

    TravelDetailScreen(
        state = state,
        clickBack = { viewModel.onIntent(TravelDetailIntent.ClickBack) },
        selectDay = { viewModel.onIntent(TravelDetailIntent.SelectDay(it)) },
        clickTimelineAutoSetting = { viewModel.onIntent(TravelDetailIntent.ClickTimelineAutoSetting) },
        clickEditTravel = { viewModel.onIntent(TravelDetailIntent.ClickEditTravel) },
        clickAddScheduleButton = { viewModel.onIntent(TravelDetailIntent.ClickAddScheduleButton) },
        checkPlaceItem = { placeId ->
            viewModel.onIntent(TravelDetailIntent.CheckPlaceItem(placeId))
        },
        checkSelectAll = { viewModel.onIntent(TravelDetailIntent.CheckSelectAll) },
        clickDeleteSelectedPlaces = { viewModel.onIntent(TravelDetailIntent.ClickDeleteSelectedPlaces) },
        confirmDeleteSelectedPlaces = { viewModel.onIntent(TravelDetailIntent.ConfirmDeleteSelectedPlaces) },
        dismissDeleteModal = { viewModel.onIntent(TravelDetailIntent.DismissDeleteModal) },
        confirmCancelEditMode = { viewModel.onIntent(TravelDetailIntent.ConfirmCancelEditMode) },
        dismissCancelEditModal = { viewModel.onIntent(TravelDetailIntent.DismissCancelEditModal) },
        longClickPlaceItem = { viewModel.onIntent(TravelDetailIntent.LongClickPlaceItem) },
        dismissTimelineBottomSheet = { viewModel.onIntent(TravelDetailIntent.DismissTimelineBottomSheet) },
        confirmTimelineSetting = { startTime -> viewModel.onIntent(TravelDetailIntent.ConfirmTimelineSetting(startTime)) },
        reorderPlaces = { fromIndex, toIndex -> viewModel.onIntent(TravelDetailIntent.ReorderPlaces(fromIndex, toIndex)) },
        confirmEditMode = { viewModel.onIntent(TravelDetailIntent.ConfirmEditMode) },
        clickPlaceItem = { viewModel.onIntent(TravelDetailIntent.ClickPlaceItem(it)) },
        dismissPlaceBottomSheet = { viewModel.onIntent(TravelDetailIntent.DismissPlaceBottomSheet) },
        navigateToPlaceDetail = { viewModel.onIntent(TravelDetailIntent.NavigateToPlaceDetail(it)) },
        clickAddTime = { viewModel.onIntent(TravelDetailIntent.ClickAddTime(it)) },
        clickAddMemo = { viewModel.onIntent(TravelDetailIntent.ClickAddMemo(it)) },
        clickAddCost = { viewModel.onIntent(TravelDetailIntent.ClickAddCost(it)) },
        clickFindRoute = { viewModel.onIntent(TravelDetailIntent.ClickFindRoute(it)) },
    )
}

@Composable
private fun TravelDetailScreen(
    state: TravelDetailState,
    clickBack: () -> Unit,
    selectDay: (Int) -> Unit,
    clickTimelineAutoSetting: () -> Unit,
    clickEditTravel: () -> Unit,
    clickAddScheduleButton: () -> Unit,
    checkPlaceItem: (placeId: Int) -> Unit,
    checkSelectAll: () -> Unit,
    clickDeleteSelectedPlaces: () -> Unit,
    confirmDeleteSelectedPlaces: () -> Unit,
    dismissDeleteModal: () -> Unit,
    confirmCancelEditMode: () -> Unit,
    dismissCancelEditModal: () -> Unit,
    longClickPlaceItem: () -> Unit,
    dismissTimelineBottomSheet: () -> Unit,
    confirmTimelineSetting: (Duration) -> Unit,
    reorderPlaces: (Int, Int) -> Unit,
    confirmEditMode: () -> Unit,
    clickPlaceItem: (TravelPlace) -> Unit,
    clickAddTime: (Int) -> Unit,
    clickAddMemo: (Int) -> Unit,
    clickAddCost: (Int) -> Unit,
    clickFindRoute: (String) -> Unit,
    dismissPlaceBottomSheet: () -> Unit,
    navigateToPlaceDetail: (String) -> Unit,
) {
    BackHandler(enabled = state.isEditMode) {
        clickBack()
    }

    val tabs = (1..state.contentInfo.days).map { day ->
        NDGLChipTabAttr.Tab(
            tag = "d$day",
            name = stringResource(R.string.day_format, day),
        )
    }.let { persistentListOf(*it.toTypedArray()) }

    val listState = rememberLazyListState()
    val isHeaderSticky by remember {
        derivedStateOf {
            val firstItem = listState.layoutInfo.visibleItemsInfo.firstOrNull()
            val result = firstItem?.index == 1 && firstItem.offset <= 0
            result
        }
    }
    var columnScrollingEnabled by remember { mutableStateOf(true) }

    val currentItineraries = if (state.isEditMode) state.tempItineraries else state.itineraries
    val currentItinerary = currentItineraries.getOrNull(state.selectedDay - 1)
    val currentPlaces = currentItinerary?.places.orEmpty()
    val currentTransportSegments = currentItinerary?.transportSegments.orEmpty()

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
                        onLeadingIconClick = clickBack,
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
                        .padding(horizontal = 24.dp)
                        .padding(bottom = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    currentItineraries.getOrNull(state.selectedDay - 1)?.let { itinerary ->
                        if (itinerary.places.isNotEmpty()) {
                            TravelMap(
                                places = itinerary.places,
                                onScrollEnabledChange = { columnScrollingEnabled = it },
                            )
                            if (state.isEditMode) {
                                val currentDayPlaceIds = itinerary.places.map { it.id }.toSet()
                                val selectedInCurrentDay = state.selectedPlaceIds.intersect(currentDayPlaceIds)
                                val isAllSelected = selectedInCurrentDay.size == itinerary.places.size
                                EditControlBar(
                                    isAllSelected = isAllSelected,
                                    onSelectAllClick = checkSelectAll,
                                    onDeleteSelectedClick = { if (state.selectedPlaceIds.isNotEmpty()) clickDeleteSelectedPlaces() },
                                )
                            } else {
                                TravelDetailToolBar(
                                    clickTimelineAutoSetting = clickTimelineAutoSetting,
                                    clickEditTravel = clickEditTravel,
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally,
                            ) {
                                Spacer(Modifier.height(80.dp))
                                Icon(
                                    imageVector = ImageVector.vectorResource(R.drawable.ic_140_no_schedule_calendar),
                                    contentDescription = null,
                                    tint = Color.Unspecified,
                                )
                                Spacer(Modifier.height(16.dp))
                                Text(
                                    text = stringResource(R.string.no_schedule_message, state.selectedDay),
                                    color = NDGLTheme.colors.black400,
                                    style = NDGLTheme.typography.bodyLgMedium,
                                )
                            }
                        }
                    }
                }
            }

            item(key = "places_${state.selectedDay}") {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clipToBounds(),
                    verticalArrangement = Arrangement.spacedBy(if (state.isEditMode) 16.dp else 10.dp),
                ) {
                    currentPlaces.forEachIndexed { index, place ->
                        key("place_${state.selectedDay}_${place.id}") {
                            if (state.isEditMode) {
                                EditablePlaceItem(
                                    place = place,
                                    checked = state.selectedPlaceIds.contains(place.id),
                                    onCheck = { checkPlaceItem(place.id) },
                                )
                            } else {
                                Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                                    PlaceItem(
                                        place = place,
                                        onClick = { clickPlaceItem(place) },
                                        onLongClick = longClickPlaceItem,
                                    )
                                }
                            }
                        }

                        if (!state.isEditMode && index < currentPlaces.size - 1) {
                            key("transport_${state.selectedDay}_${place.id}") {
                                currentTransportSegments.getOrNull(index)?.let { segment ->
                                    Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                                        TransportSegment(segment = segment)
                                    }
                                }
                            }
                        }
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
            if (state.isEditMode) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(color = NDGLTheme.colors.black50)
                            .clickable {
                                clickAddScheduleButton()
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_24_plus), contentDescription = null)
                    }
                    NDGLCTAButton(
                        modifier = Modifier.fillMaxWidth(),
                        type = NDGLCTAButtonAttr.Type.PRIMARY,
                        size = NDGLCTAButtonAttr.Size.LARGE,
                        status = NDGLCTAButtonAttr.Status.ACTIVE,
                        label = stringResource(R.string.edit_done),
                        onClick = confirmEditMode,
                    )
                }
            } else {
                NDGLCTAButton(
                    modifier = Modifier.fillMaxWidth(),
                    type = NDGLCTAButtonAttr.Type.PRIMARY,
                    size = NDGLCTAButtonAttr.Size.LARGE,
                    status = NDGLCTAButtonAttr.Status.ACTIVE,
                    label = stringResource(R.string.add_schedule),
                    onClick = clickAddScheduleButton,
                )
            }
        }

        if (state.showDeleteModal) {
            NDGLModal(
                onDismissRequest = dismissDeleteModal,
                title = stringResource(R.string.delete_place_dialog_title),
                body = stringResource(R.string.delete_place_dialog_message),
                negativeButtonText = stringResource(R.string.delete_place_dialog_cancel),
                onNegativeButtonClick = dismissDeleteModal,
                positiveButtonText = stringResource(R.string.delete_place_dialog_confirm),
                onPositiveButtonClick = confirmDeleteSelectedPlaces,
            )
        }

        if (state.showCancelEditModal) {
            NDGLModal(
                onDismissRequest = dismissCancelEditModal,
                title = stringResource(R.string.cancel_edit_dialog_title),
                body = stringResource(R.string.cancel_edit_dialog_message),
                negativeButtonText = stringResource(R.string.cancel_edit_dialog_cancel),
                onNegativeButtonClick = confirmCancelEditMode,
                positiveButtonText = stringResource(R.string.cancel_edit_dialog_confirm),
                onPositiveButtonClick = dismissCancelEditModal,
            )
        }

        if (state.showTimelineBottomSheet) {
            NDGLBottomSheet(
                onDismissRequest = dismissTimelineBottomSheet,
                showDragHandle = false,
            ) {
                TimelineContent(
                    startTime = state.startTime,
                    endTime = state.endTime,
                    onDismissRequest = dismissTimelineBottomSheet,
                    onConfirm = confirmTimelineSetting,
                )
            }
        }

        if (state.showPlaceBottomSheet && state.selectedPlace != null) {
            // FIXME
            PlaceBottomSheet(
                place = state.selectedPlace,
                onDismiss = dismissPlaceBottomSheet,
                navigateToPlaceDetail = { navigateToPlaceDetail(state.selectedPlace.googlePlaceId) },
                onAddTimeClick = clickAddTime,
                onAddCostClick = clickAddCost,
                onAddMemoClick = clickAddMemo,
                onFindRouteClick = clickFindRoute,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelDetailScreenPreview() {
    NDGLTheme {
        TravelDetailScreen(
            state = TravelDetailState(
                contentInfo = ContentInfo(
                    travelId = "TRAVEL_001",
                    country = "태국",
                    city = "방콕",
                    budgetPerPerson = Budget(1200000),
                    nights = 3,
                    days = 4,
                    videoInfo = VideoInfo(
                        title = "방콕 풀코스, 동남아 안 가본 곽튜브와 함께 【방콕】",
                        name = "빠니보틀",
                        profileImage = "",
                        thumbnail = "",
                        link = "",
                        summary = "빠니보틀은 주말을 이용해 직장인들도 충분히 다녀올 수 있는 '금요일 퇴근 후 방콕 여행'의 가능성을 보여주며, 곽튜브와의 티격태격 케미를 통해 방콕의 매력을 소개합니다",
                    ),
                ),
                selectedDay = 1,
                itineraries = listOf(
                    Itinerary(
                        budget = Budget(300000),
                        places = listOf(
                            TravelPlace(
                                id = 1,
                                day = 1,
                                sequence = 1,
                                estimatedDuration = 90.minutes,
                                googlePlaceId = "",
                                thumbnail = "",
                                latitude = 35.6585805,
                                longitude = 139.7454329,
                                name = "도쿄 타워",
                                description = "도쿄 타워 설명",
                                regularOpeningHours = "09:00~23:00",
                                googleMapsUri = "",
                                placeType = PlaceType.ATTRACTION,
                            ),
                            TravelPlace(
                                id = 2,
                                day = 1,
                                sequence = 2,
                                estimatedDuration = 60.minutes,
                                googlePlaceId = "",
                                thumbnail = "",
                                latitude = 35.6654,
                                longitude = 139.7707,
                                name = "츠키지 스시 다이",
                                description = "츠키지 스시 다이 설명",
                                regularOpeningHours = "11:00~22:00",
                                googleMapsUri = "",
                                placeType = PlaceType.RESTAURANT,
                            ),
                        ),
                        transportSegments = listOf(
                            TransportSegment(
                                type = TransportType.CAR,
                                duration = 25.minutes,
                                distance = 3500,
                            ),
                        ),
                    ),
                ),
                isEditMode = false,
                selectedPlaceIds = emptySet(),
            ),
            clickBack = {},
            selectDay = {},
            clickTimelineAutoSetting = {},
            clickEditTravel = {},
            clickAddScheduleButton = {},
            checkPlaceItem = {},
            checkSelectAll = {},
            clickDeleteSelectedPlaces = {},
            confirmDeleteSelectedPlaces = {},
            dismissDeleteModal = {},
            confirmCancelEditMode = {},
            dismissCancelEditModal = {},
            longClickPlaceItem = {},
            dismissTimelineBottomSheet = {},
            confirmTimelineSetting = {},
            reorderPlaces = { _, _ -> },
            confirmEditMode = {},
            clickPlaceItem = {},
            clickAddTime = {},
            clickAddMemo = {},
            clickAddCost = {},
            clickFindRoute = {},
            dismissPlaceBottomSheet = {},
            navigateToPlaceDetail = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelDetailScreenEditModePreview() {
    NDGLTheme {
        TravelDetailScreen(
            state = TravelDetailState(
                contentInfo = ContentInfo(
                    travelId = "TRAVEL_001",
                    country = "태국",
                    city = "방콕",
                    budgetPerPerson = Budget(1200000),
                    nights = 3,
                    days = 4,
                    videoInfo = VideoInfo(
                        title = "방콕 풀코스, 동남아 안 가본 곽튜브와 함께 【방콕】",
                        name = "빠니보틀",
                        profileImage = "",
                        thumbnail = "",
                        link = "",
                        summary = "빠니보틀은 주말을 이용해 직장인들도 충분히 다녀올 수 있는 '금요일 퇴근 후 방콕 여행'의 가능성을 보여주며, 곽튜브와의 티격태격 케미를 통해 방콕의 매력을 소개합니다",
                    ),
                ),
                selectedDay = 1,
                itineraries = listOf(
                    Itinerary(
                        budget = Budget(300000),
                        places = listOf(
                            TravelPlace(
                                id = 1,
                                day = 1,
                                sequence = 1,
                                estimatedDuration = 90.minutes,
                                googlePlaceId = "",
                                thumbnail = "",
                                latitude = 35.6585805,
                                longitude = 139.7454329,
                                name = "도쿄 타워",
                                description = "도쿄 타워 설명",
                                regularOpeningHours = "09:00~23:00",
                                googleMapsUri = "",
                                placeType = PlaceType.ATTRACTION,
                            ),
                            TravelPlace(
                                id = 2,
                                day = 1,
                                sequence = 2,
                                estimatedDuration = 60.minutes,
                                googlePlaceId = "",
                                thumbnail = "",
                                latitude = 35.6654,
                                longitude = 139.7707,
                                name = "츠키지 스시 다이",
                                description = "츠키지 스시 다이 설명",
                                regularOpeningHours = "11:00~22:00",
                                googleMapsUri = "",
                                placeType = PlaceType.RESTAURANT,
                            ),
                        ),
                        transportSegments = listOf(
                            TransportSegment(
                                type = TransportType.CAR,
                                duration = 25.minutes,
                                distance = 3500,
                            ),
                        ),
                    ),
                ),
                isEditMode = true,
                selectedPlaceIds = setOf(1),
            ),
            clickBack = {},
            selectDay = {},
            clickTimelineAutoSetting = {},
            clickEditTravel = {},
            clickAddScheduleButton = {},
            checkPlaceItem = {},
            checkSelectAll = {},
            clickDeleteSelectedPlaces = {},
            confirmDeleteSelectedPlaces = {},
            dismissDeleteModal = {},
            confirmCancelEditMode = {},
            dismissCancelEditModal = {},
            longClickPlaceItem = {},
            dismissTimelineBottomSheet = {},
            confirmTimelineSetting = {},
            reorderPlaces = { _, _ -> },
            confirmEditMode = {},
            clickPlaceItem = {},
            clickAddTime = {},
            clickAddMemo = {},
            clickAddCost = {},
            clickFindRoute = {},
            dismissPlaceBottomSheet = {},
            navigateToPlaceDetail = {},
        )
    }
}
