package com.yapp.ndgl.feature.home.main

import androidx.lifecycle.viewModelScope
import com.yapp.ndgl.core.base.BaseViewModel
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.util.suspendRunCatching
import com.yapp.ndgl.data.auth.repository.AuthRepository
import com.yapp.ndgl.data.travel.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val homeRepository: HomeRepository,
) : BaseViewModel<HomeState, HomeIntent, HomeSideEffect>(
    initialState = HomeState(),
) {
    init {
        initSession()
    }

    private fun initSession() = viewModelScope.launch {
        suspendRunCatching {
            authRepository.initSession()
        }.onSuccess {
            loadHomeContents()
        }.onFailure { exception ->
            Timber.e("fail to init session: $exception")
            loadHomeContents()
        }
    }

    private fun loadHomeContents() {
        loadMyTravel()
        loadPopularTravel()
        loadRecommendedTravel()
    }

    private fun loadMyTravel() {
        viewModelScope.launch {
            suspendRunCatching { homeRepository.getMyTravel() }
                .onSuccess { travel ->
                    reduce {
                        copy(
                            myTravel = HomeState.MyTravel.InProgress(
                                title = travel.title,
                                dayCount = travel.dayCount,
                                startDate = travel.startDate,
                                endDate = travel.endDate,
                                currentPlace = HomeState.TravelPlace(
                                    category = travel.currentPlace.category,
                                    estimatedTime = travel.currentPlace.estimatedTime,
                                    name = travel.currentPlace.name,
                                    thumbnailUrl = travel.currentPlace.thumbnailUrl,
                                ),
                            ),
                        )
                    }
                }
        }
    }

    private fun loadPopularTravel() {
        viewModelScope.launch {
            suspendRunCatching { homeRepository.getPopularTravels() }
                .onSuccess { travels ->
                    val travelsByYoutuber = travels.groupBy { travel ->
                        travel.youtube.youtuber
                    }
                    val tabs = travelsByYoutuber.keys
                        .map { youtuber ->
                            HomeState.PopularTravelTab(
                                tag = youtuber,
                                name = youtuber,
                                icon = R.drawable.ic_20_video,
                            )
                        }.toMutableList()
                        .apply {
                            add(
                                index = 0,
                                element = HomeState.PopularTravelTab(
                                    tag = "all",
                                    name = "전체",
                                    icon = null,
                                ),
                            )
                        }.toList()
                    val travelsByTab = travelsByYoutuber.toMutableMap().apply {
                        put("all", travels)
                    }
                    reduce {
                        copy(
                            popularTravelTabs = tabs,
                            popularTravelsByTab = travelsByTab,
                        )
                    }
                }
        }
    }

    private fun loadRecommendedTravel() {
        viewModelScope.launch {
            suspendRunCatching { homeRepository.getRecommendedTravels() }
                .onSuccess { travels ->
                    reduce { copy(recommendedContents = travels) }
                }
        }
    }

    override suspend fun handleIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.SelectPopularTravelTab -> {
                reduce { copy(popularTravelSelectedTabIndex = intent.index) }
            }
        }
    }
}
