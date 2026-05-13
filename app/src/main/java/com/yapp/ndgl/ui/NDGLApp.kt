package com.yapp.ndgl.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.yapp.ndgl.feature.contentrecommendation.navigation.contentRecommendationEntry
import com.yapp.ndgl.feature.home.navigation.homeEntry
import com.yapp.ndgl.feature.travel.navigation.travelEntry
import com.yapp.ndgl.feature.travelhelper.navigation.travelHelperEntry
import com.yapp.ndgl.navigation.BottomNavTab
import com.yapp.ndgl.navigation.Navigator
import com.yapp.ndgl.navigation.Route
import com.yapp.ndgl.navigation.rememberNavigationState
import com.yapp.ndgl.navigation.toEntries

@Composable
fun NDGLApp() {
    val navigationState = rememberNavigationState(
        startRoute = Route.Home,
        topLevelKeys = BottomNavTab.entries.map { it.route }.toSet(),
    )
    val navigator = remember { Navigator(navigationState) }
    val shouldShowBottomBar =
        remember(navigationState.currentKey) { navigationState.currentKey in navigationState.topLevelKeys }

    Box(modifier = Modifier.fillMaxSize()) {
        val entryProvider = entryProvider {
            homeEntry(navigator)
            travelEntry(navigator)
            travelHelperEntry(navigator)
            contentRecommendationEntry(navigator)
        }

        NavDisplay(
            modifier = Modifier.fillMaxSize(),
            onBack = navigator::goBack,
            entries = navigationState.toEntries(entryProvider),
        )

        AnimatedVisibility(
            modifier = Modifier.align(Alignment.BottomCenter),
            visible = shouldShowBottomBar,
        ) {
            BottomNavigationBar(
                currentTab = navigationState.currentTopLevelKey as Route,
                onTabSelected = { key -> navigator.navigate(key) },
            )
        }
    }
}
