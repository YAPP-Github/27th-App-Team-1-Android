package com.yapp.ndgl.feature.travel.traveldetail

import com.yapp.ndgl.core.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

// TODO("테스트용으로 지워야함")
private const val TEST_PROFILE_IMAGE_URL =
    "https://yt3.ggpht.com/Sr5y4IxegXCEZ0SYNvFB749crrAZmNpurZqfq2KvPEpiCYeakoMjBWMnW_56rMuYW_HipJOBRtU=s88-c-k-c0x00ffffff-no-rj"
private const val TEST_THUMBNAIL_URL =
    "https://picsum.photos/200"

@HiltViewModel(assistedFactory = TravelDetailViewModel.Factory::class)
class TravelDetailViewModel @AssistedInject constructor(
    @Assisted private val travelId: Int,
) : BaseViewModel<TravelDetailState, TravelDetailIntent, TravelDetailSideEffect>(
    initialState = TravelDetailState(),
) {
    init {
        loadTravelData()
    }

    private fun loadTravelData() {
        // TODO: Load from repository
        val loadedItineraries = listOf(
            Itinerary(
                budget = Budget(300000),
                places = listOf(
                    TravelPlace(
                        id = 1,
                        day = 1,
                        sequence = 1,
                        estimatedDuration = 90.minutes,
                        googlePlaceId = "ChIJCewJkL2LGGAR3Qmk0vCTGkg",
                        thumbnail = TEST_THUMBNAIL_URL,
                        latitude = 35.6585805,
                        longitude = 139.7454329,
                        name = "도쿄 타워",
                        regularOpeningHours = "09:00~23:00",
                        googleMapsUri = "",
                        placeType = PlaceType.ATTRACTION,
                        description = "장소에 대한 설명 추가",
                    ),
                    TravelPlace(
                        id = 2,
                        day = 1,
                        sequence = 2,
                        estimatedDuration = 60.minutes,
                        googlePlaceId = "ChIJexample2",
                        thumbnail = TEST_THUMBNAIL_URL,
                        latitude = 35.6654,
                        longitude = 139.7707,
                        name = "츠키지 스시 다이",
                        regularOpeningHours = "11:00~22:00",
                        googleMapsUri = "",
                        placeType = PlaceType.RESTAURANT,
                        description = "장소에 대한 설명 추가",
                    ),
                    TravelPlace(
                        id = 3,
                        day = 1,
                        sequence = 3,
                        estimatedDuration = 120.minutes,
                        googlePlaceId = "ChIJexample3",
                        thumbnail = TEST_THUMBNAIL_URL,
                        latitude = 35.6812,
                        longitude = 139.7671,
                        name = "신주쿠 프린스 호텔",
                        regularOpeningHours = "24시간",
                        googleMapsUri = "",
                        placeType = PlaceType.ACCOMMODATION,
                        description = "장소에 대한 설명 추가",
                    ),
                    TravelPlace(
                        id = 4,
                        day = 1,
                        sequence = 4,
                        estimatedDuration = 90.minutes,
                        googlePlaceId = "ChIJexample7",
                        thumbnail = TEST_THUMBNAIL_URL,
                        latitude = 35.6944,
                        longitude = 139.7006,
                        name = "오모이데 요코초",
                        regularOpeningHours = "17:00~24:00",
                        googleMapsUri = "",
                        placeType = PlaceType.RESTAURANT,
                        description = "장소에 대한 설명 추가",
                    ),
                ),
                transportSegments = listOf(
                    TransportSegment(type = TransportType.CAR, duration = 25.minutes, distance = 3500),
                    TransportSegment(type = TransportType.WALK, duration = 10.minutes, distance = 800),
                    TransportSegment(type = TransportType.CAR, duration = 15.minutes, distance = 2100),
                ),
            ),
            Itinerary(
                budget = Budget(250000),
                places = listOf(
                    TravelPlace(
                        id = 8,
                        day = 2,
                        sequence = 1,
                        estimatedDuration = 90.minutes,
                        googlePlaceId = "ChIJexample4",
                        thumbnail = TEST_THUMBNAIL_URL,
                        latitude = 35.7148,
                        longitude = 139.7967,
                        name = "센소지 절",
                        regularOpeningHours = "06:00~17:00",
                        googleMapsUri = "",
                        placeType = PlaceType.ATTRACTION,
                        description = "장소에 대한 설명 추가",
                    ),
                    TravelPlace(
                        id = 9,
                        day = 2,
                        sequence = 2,
                        estimatedDuration = 45.minutes,
                        googlePlaceId = "ChIJexample5",
                        thumbnail = TEST_THUMBNAIL_URL,
                        latitude = 35.7120,
                        longitude = 139.7960,
                        name = "아사쿠사 카페",
                        regularOpeningHours = "08:00~20:00",
                        googleMapsUri = "",
                        placeType = PlaceType.CAFE,
                        description = "장소에 대한 설명 추가",
                    ),
                ),
                transportSegments = listOf(
                    TransportSegment(type = TransportType.WALK, duration = 8.minutes, distance = 600),
                    TransportSegment(type = TransportType.TRAIN, duration = 30.minutes, distance = 8500),
                ),
            ),
        )

        reduce {
            copy(
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
                        profileImage = TEST_PROFILE_IMAGE_URL,
                        thumbnail = TEST_THUMBNAIL_URL,
                        link = "https://www.youtube.com/watch?v=F2utz6L76D0",
                        summary = "빠니보틀은 주말을 이용해 직장인들도 충분히 다녀올 수 있는 '금요일 퇴근 후 방콕 여행'의 가능성을 보여주며, 곽튜브와의 티격태격 케미를 통해 방콕의 매력을 소개합니다",
                    ),
                ),
                itineraries = loadedItineraries,
                tempItineraries = loadedItineraries,
            )
        }
    }

    override suspend fun handleIntent(intent: TravelDetailIntent) {
        when (intent) {
            is TravelDetailIntent.SelectDay -> selectDay(intent.day)
            is TravelDetailIntent.ClickTimelineAutoSetting -> clickTimelineAutoSetting()
            is TravelDetailIntent.ClickEditTravel -> clickEditTravel()
            is TravelDetailIntent.ClickAddScheduleButton -> clickAddScheduleButton()
            is TravelDetailIntent.CheckPlaceItem -> checkPlaceItem(intent.placeId)
            is TravelDetailIntent.CheckSelectAll -> checkSelectAll()
            is TravelDetailIntent.ClickDeleteSelectedPlaces -> clickDeleteSelectedPlaces()
            is TravelDetailIntent.ConfirmDeleteSelectedPlaces -> confirmDeleteSelectedPlaces()
            is TravelDetailIntent.DismissDeleteModal -> dismissDeleteModal()
            is TravelDetailIntent.ClickBack -> clickBack()
            is TravelDetailIntent.ConfirmCancelEditMode -> confirmCancelEditMode()
            is TravelDetailIntent.DismissCancelEditModal -> dismissCancelEditModal()
            is TravelDetailIntent.LongClickPlaceItem -> longClickPlaceItem()
            is TravelDetailIntent.DismissTimelineBottomSheet -> dismissTimelineBottomSheet()
            is TravelDetailIntent.ConfirmTimelineSetting -> confirmTimelineSetting(intent.startTime)
            is TravelDetailIntent.ReorderPlaces -> reorderPlaces(intent.fromIndex, intent.toIndex)
            is TravelDetailIntent.ConfirmEditMode -> confirmEditMode()
        }
    }

    private fun selectDay(day: Int) {
        reduce { copy(selectedDay = day) }
    }

    private fun clickTimelineAutoSetting() {
        reduce { copy(showTimelineBottomSheet = true) }
    }

    private fun clickEditTravel() {
        reduce {
            copy(
                isEditMode = true,
                selectedPlaceIds = emptySet(),
            )
        }
    }

    private fun clickAddScheduleButton() {
        // TODO: Handle add schedule
    }

    private fun checkPlaceItem(placeId: Int) {
        reduce {
            copy(
                selectedPlaceIds = if (selectedPlaceIds.contains(placeId)) {
                    selectedPlaceIds - placeId
                } else {
                    selectedPlaceIds + placeId
                },
            )
        }
    }

    private fun checkSelectAll() {
        reduce {
            val currentItineraries = if (isEditMode) tempItineraries else itineraries
            val currentDayPlaceIds = currentItineraries.getOrNull(selectedDay - 1)
                ?.places?.map { it.id }?.toSet() ?: emptySet()

            copy(
                selectedPlaceIds = if (selectedPlaceIds.size == currentDayPlaceIds.size) {
                    emptySet()
                } else {
                    currentDayPlaceIds
                },
            )
        }
    }

    private fun clickBack() {
        if (state.value.isEditMode) {
            reduce { copy(showCancelEditModal = true) }
        } else {
            postSideEffect(TravelDetailSideEffect.NavigateBack)
        }
    }

    private fun clickDeleteSelectedPlaces() {
        reduce { copy(showDeleteModal = true) }
    }

    private fun confirmDeleteSelectedPlaces() {
        reduce {
            val updatedItineraries = tempItineraries.map { itinerary ->
                val remainingPlaces = itinerary.places
                    .filter { it.id !in selectedPlaceIds }
                    .mapIndexed { newIndex, place ->
                        place.copy(sequence = newIndex + 1)
                    }

                // TODO Place 삭제하면 교통수단 어떻게 다시 들어갈지 고민 필요
                val remainingTransportCount = (remainingPlaces.size - 1).coerceAtLeast(0)
                val remainingTransports = itinerary.transportSegments.take(remainingTransportCount)
                itinerary.copy(
                    places = remainingPlaces,
                    transportSegments = remainingTransports,
                )
            }

            copy(
                tempItineraries = updatedItineraries,
                selectedPlaceIds = emptySet(),
                showDeleteModal = false,
            )
        }
    }

    private fun dismissDeleteModal() {
        reduce { copy(showDeleteModal = false) }
    }

    private fun confirmCancelEditMode() {
        reduce {
            copy(
                isEditMode = false,
                selectedPlaceIds = emptySet(),
                showCancelEditModal = false,
                tempItineraries = itineraries,
            )
        }
    }

    private fun dismissCancelEditModal() {
        reduce { copy(showCancelEditModal = false) }
    }

    private fun longClickPlaceItem() {
        reduce {
            copy(
                isEditMode = true,
                selectedPlaceIds = emptySet(),
            )
        }
    }

    private fun dismissTimelineBottomSheet() {
        reduce { copy(showTimelineBottomSheet = false) }
    }

    private fun confirmTimelineSetting(startTime: Duration) {
        changeStartTime(startTime)
        reduce { copy(showTimelineBottomSheet = false) }
    }

    private fun changeStartTime(duration: Duration) {
        reduce {
            copy(
                startTime = duration,
                endTime = duration + 15.hours, // FIXME
            )
        }
    }

    private fun reorderPlaces(fromIndex: Int, toIndex: Int) {
        reduce {
            val updatedItineraries = tempItineraries.map { itinerary ->
                val mutablePlaces = itinerary.places.toMutableList()

                if (fromIndex in mutablePlaces.indices && toIndex in mutablePlaces.indices) {
                    val movedItem = mutablePlaces.removeAt(fromIndex)
                    mutablePlaces.add(toIndex, movedItem)

                    val reorderedPlaces = mutablePlaces.mapIndexed { index, place ->
                        place.copy(sequence = index + 1)
                    }

                    itinerary.copy(
                        places = reorderedPlaces,
                    )
                } else {
                    itinerary
                }
            }

            copy(tempItineraries = updatedItineraries)
        }
    }

    private fun confirmEditMode() {
        reduce {
            copy(
                itineraries = tempItineraries,
                isEditMode = false,
                selectedPlaceIds = emptySet(),
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(travelId: Int): TravelDetailViewModel
    }
}
