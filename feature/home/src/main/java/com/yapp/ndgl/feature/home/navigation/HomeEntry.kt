package com.yapp.ndgl.feature.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.yapp.ndgl.feature.home.main.HomeRoute
import com.yapp.ndgl.navigation.Navigator
import com.yapp.ndgl.navigation.Route

fun EntryProviderScope<NavKey>.homeEntry(navigator: Navigator, innerPadding: PaddingValues) {
    entry<Route.Home> {
        HomeRoute(innerPadding = innerPadding)
    }
}
