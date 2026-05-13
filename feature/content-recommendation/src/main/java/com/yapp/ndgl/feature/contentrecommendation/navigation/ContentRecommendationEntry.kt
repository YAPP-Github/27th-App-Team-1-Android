package com.yapp.ndgl.feature.contentrecommendation.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.yapp.ndgl.feature.contentrecommendation.ContentRecommendationRoute
import com.yapp.ndgl.navigation.Navigator
import com.yapp.ndgl.navigation.Route

fun EntryProviderScope<NavKey>.contentRecommendationEntry(navigator: Navigator) {
    entry<Route.ContentRecommendation> {
        ContentRecommendationRoute(
            navigateBack = { navigator.goBack() },
        )
    }
}
