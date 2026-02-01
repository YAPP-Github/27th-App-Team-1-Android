package com.yapp.ndgl.feature.travel.followtravel

import com.yapp.ndgl.core.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.time.Duration.Companion.minutes

// TODO("테스트용으로 지워야함")
private const val TEST_PROFILE_IMAGE_URL =
    "https://yt3.ggpht.com/Sr5y4IxegXCEZ0SYNvFB749crrAZmNpurZqfq2KvPEpiCYeakoMjBWMnW_56rMuYW_HipJOBRtU=s88-c-k-c0x00ffffff-no-rj"
private const val TEST_THUMBNAIL_URL =
    "https://picsum.photos/200"

@HiltViewModel(assistedFactory = FollowTravelViewModel.Factory::class)
class FollowTravelViewModel @AssistedInject constructor(
    @Assisted private val travelId: Int,
) : BaseViewModel<FollowTravelState, FollowTravelIntent, FollowTravelSideEffect>(
    initialState = FollowTravelState(),
) {
    init {
        loadTravelData()
    }

    private fun loadTravelData() {
        // TODO: Load from repository
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
                itineraries = listOf(
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
                            ),
                        ),
                        transportSegments = listOf(
                            TransportSegment(type = TransportType.WALK, duration = 8.minutes, distance = 600),
                            TransportSegment(type = TransportType.TRAIN, duration = 30.minutes, distance = 8500),
                        ),
                    ),
                ),
            )
        }
    }

    override suspend fun handleIntent(intent: FollowTravelIntent) {
        when (intent) {
            is FollowTravelIntent.SelectDay -> {
                reduce { copy(selectedDay = intent.day) }
            }

            is FollowTravelIntent.ClickFollowTravel -> {
                // TODO: Handle follow
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(travelId: Int): FollowTravelViewModel
    }
}
