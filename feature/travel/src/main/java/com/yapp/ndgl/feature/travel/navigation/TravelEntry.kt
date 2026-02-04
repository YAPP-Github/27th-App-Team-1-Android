package com.yapp.ndgl.feature.travel.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.yapp.ndgl.feature.travel.TravelRoute
import com.yapp.ndgl.feature.travel.datepicker.DatePickerRoute
import com.yapp.ndgl.feature.travel.datepicker.DatePickerViewModel
import com.yapp.ndgl.feature.travel.followtravel.FollowTravelRoute
import com.yapp.ndgl.feature.travel.followtravel.FollowTravelViewModel
import com.yapp.ndgl.navigation.Navigator
import com.yapp.ndgl.navigation.Route

fun EntryProviderScope<NavKey>.travelEntry(navigator: Navigator, innerPadding: PaddingValues) {
    entry<Route.Travel> {
        TravelRoute(
            navigateToFollowTravel = { travelId ->
                navigator.navigate(Route.FollowTravel(travelId))
            },
            innerPadding = innerPadding,
        )
    }
    entry<Route.FollowTravel> { route ->
        val viewModel =
            hiltViewModel<FollowTravelViewModel, FollowTravelViewModel.Factory> { factory ->
                factory.create(travelId = route.travelId)
            }
        FollowTravelRoute(
            viewModel = viewModel,
            navigateBack = { navigator.goBack() },
            navigateToDatePicker = { tripDays ->
                navigator.navigate(Route.DatePicker(tripDays))
            },
        )
    }
    entry<Route.DatePicker> { route ->
        val viewModel =
            hiltViewModel<DatePickerViewModel, DatePickerViewModel.Factory> { factory ->
                factory.create(tripDays = route.tripDays)
            }
        DatePickerRoute(
            viewModel = viewModel,
            navigateBack = { navigator.goBack() },
            innerPadding = innerPadding,
        )
    }
}
