package com.yapp.ndgl.feature.travelhelper.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.yapp.ndgl.feature.travelhelper.TravelHelperRoute
import com.yapp.ndgl.navigation.Navigator
import com.yapp.ndgl.navigation.Route

fun EntryProviderScope<NavKey>.travelHelperEntry(navigator: Navigator, innerPadding: PaddingValues) {
    entry<Route.TravelHelper> {
        TravelHelperRoute(innerPadding = innerPadding)
    }
}
