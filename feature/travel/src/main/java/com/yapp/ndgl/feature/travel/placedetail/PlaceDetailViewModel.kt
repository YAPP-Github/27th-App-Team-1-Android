package com.yapp.ndgl.feature.travel.placedetail

import com.yapp.ndgl.core.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlin.time.Duration.Companion.hours

@HiltViewModel(assistedFactory = PlaceDetailViewModel.Factory::class)
class PlaceDetailViewModel @AssistedInject constructor(
    @Assisted private val placeId: String,
) : BaseViewModel<PlaceDetailState, PlaceDetailIntent, PlaceDetailSideEffect>(
    initialState = PlaceDetailState(),
) {
    init {
        loadPlaceData()
    }

    private fun loadPlaceData() {
        // TODO: Load from repository (현재는 테스트를 위한 더미 데이터)
        reduce {
            copy(
                placeInfo = PlaceInfo(
                    id = placeId,
                    name = "젤라테리아 파씨 (Gelateria Fassi)",
                    category = "이탈리아 • 젤라또 • 디저트 카페",
                    address = "Via Principe Eugenio, 65, 00185 Roma RM, Italy",
                    phoneNumber = "+39 06 446 4740",
                    openingHours = "매일 12:00 ~ 24:00",
                    googleMapsUri = "https://maps.google.com/?cid=14776686710302251978&g_mp=CiVnb29nbGUubWFwcy" +
                        "5wbGFjZXMudjEuUGxhY2VzLkdldFBsYWNlEAIYBCAA",
                    websiteUrl = "https://www.gyukatsu-motomura.com/shop/shinjukuhonten",
                    rating = 4.7,
                    userRatingCount = 12450,
                    estimatedDuration = 1.hours,
                    thumbnail = "https://images.unsplash.com/photo-1567206563064-6f60f40a2b57",
                    tips = listOf(
                        "리조(쌀) 맛은 무조건 드셔보세요. 파씨의 시그니처입니다.",
                        "생크림(Panna)을 무료로 올려주니 꼭 추가해서 드세요!",
                        "매장 내부에 앉아서 먹을 수 있는 공간이 꽤 넓습니다.",
                    ),
                    alternativePlaces = listOf(
                        AlternativePlace(
                            id = 1,
                            name = "폼피 티라미수 (Pompi Tiramisu)",
                            thumbnail = "https://images.unsplash.com/photo-1571877227200-a0d98ea607e9",
                            placeType = PlaceType.CAFE,
                        ),
                        AlternativePlace(
                            id = 2,
                            name = "지올리띠 (Giolitti)",
                            thumbnail = "https://images.unsplash.com/photo-1505394033343-43adc2f44bb2",
                            placeType = PlaceType.CAFE,
                        ),
                        AlternativePlace(
                            id = 3,
                            name = "라 로칸다 디 바코 (La Locanda di Bacco)",
                            thumbnail = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5",
                            placeType = PlaceType.RESTAURANT,
                        ),
                    ),
                    latitude = 41.9028,
                    longitude = 12.4964,
                    creatorName = "빠니보틀",
                ),
                photos = listOf(
                    PlacePhoto(url = "https://picsum.photos/id/10/400/600", width = 400, height = 600),
                    PlacePhoto(url = "https://picsum.photos/id/20/600/400", width = 600, height = 400),
                    PlacePhoto(url = "https://picsum.photos/id/30/400/400", width = 400, height = 400),
                    PlacePhoto(url = "https://picsum.photos/id/40/400/500", width = 400, height = 500),
                    PlacePhoto(url = "https://picsum.photos/id/50/500/400", width = 500, height = 400),
                    PlacePhoto(url = "https://picsum.photos/id/60/400/300", width = 400, height = 300),
                    PlacePhoto(url = "https://picsum.photos/id/70/300/400", width = 300, height = 400),
                    PlacePhoto(url = "https://picsum.photos/id/80/400/450", width = 400, height = 450),
                ),
            )
        }
    }

    override suspend fun handleIntent(intent: PlaceDetailIntent) {
        when (intent) {
            is PlaceDetailIntent.SelectTab -> selectTab(intent.tab)
            is PlaceDetailIntent.ClickChangePlace -> clickChangePlace(intent.alternativePlace)
            is PlaceDetailIntent.ConfirmChangePlace -> confirmChangePlace()
            is PlaceDetailIntent.DismissChangeModal -> dismissChangeModal()
            is PlaceDetailIntent.ClickAddress -> clickAddress()
            is PlaceDetailIntent.ClickMenu -> clickMenu()
            is PlaceDetailIntent.ClickAddScheduleButton -> clickAddScheduleButton()
        }
    }

    private fun selectTab(tab: PlaceDetailTab) {
        reduce { copy(selectedTab = tab) }
    }

    private fun clickChangePlace(alternativePlace: AlternativePlace) {
        reduce { copy(selectedAlternativePlace = alternativePlace, showChangeModal = true) }
    }

    private fun confirmChangePlace() {
        reduce {
            copy(
                showChangeModal = false,
            )
        }
    }

    private fun dismissChangeModal() {
        reduce { copy(showChangeModal = false) }
    }

    private fun clickAddress() {
        postSideEffect(PlaceDetailSideEffect.NavigateToBrowser(state.value.placeInfo.googleMapsUri))
    }

    private fun clickMenu() {
        postSideEffect(PlaceDetailSideEffect.NavigateToBrowser(state.value.placeInfo.websiteUrl))
    }

    private fun clickAddScheduleButton() {
        // TODO
    }

    @AssistedFactory
    interface Factory {
        fun create(placeId: String): PlaceDetailViewModel
    }
}
