package com.yapp.ndgl.feature.home.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import com.yapp.ndgl.feature.home.main.HomeRoute
import com.yapp.ndgl.navigation.Navigator
import com.yapp.ndgl.navigation.Route

fun EntryProviderScope<NavKey>.homeEntry(navigator: Navigator) {
    entry<Route.Home> {
        HomeRoute()
    }
}
