package com.yapp.ndgl.feature.home.settings

import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBar
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBarAttr
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.ui.util.launchBrowser
import com.yapp.ndgl.feature.home.R
import com.yapp.ndgl.feature.home.settings.SettingsState.SettingsMenu
import kotlinx.collections.immutable.ImmutableList
import com.yapp.ndgl.core.ui.R as CoreR

@Composable
internal fun SettingsRoute(
    viewModel: SettingsViewModel = hiltViewModel(),
    goBack: () -> Unit,
    navigateToContentRecommendation: () -> Unit,
) {
    val context = LocalContext.current

    val state by viewModel.collectAsState()

    SettingsScreen(
        menuItems = state.menuItems,
        goBack = goBack,
        onUrlItemClick = {
            viewModel.onIntent(SettingsIntent.ClickUrlMenu(it))
        },
        onCopyIdentifierCodeClick = {
            viewModel.onIntent(SettingsIntent.ClickCopyIdentifierCodeMenu)
        },
        onContentRecommendationClick = {
            viewModel.onIntent(SettingsIntent.ClickContentRecommendationMenu)
        },
    )

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SettingsSideEffect.OpenUrl -> context.launchBrowser(sideEffect.url)
            is SettingsSideEffect.CopyIdentifierCode -> {
                val clipboard = context.getSystemService(ClipboardManager::class.java)
                val clip = ClipData.newPlainText(
                    context.getString(R.string.home_settings_identification_code),
                    sideEffect.code,
                )
                clipboard?.setPrimaryClip(clip)
            }
            SettingsSideEffect.NavigateToContentRecommendation -> navigateToContentRecommendation()
        }
    }
}

@Composable
private fun SettingsScreen(
    menuItems: ImmutableList<SettingsMenu>,
    goBack: () -> Unit,
    onUrlItemClick: (SettingsState.UrlMenu) -> Unit,
    onCopyIdentifierCodeClick: () -> Unit,
    onContentRecommendationClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            NDGLNavigationBar(
                textAlignType = NDGLNavigationBarAttr.TextAlignType.CENTER,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = NDGLTheme.colors.white)
                    .statusBarsPadding(),
                headline = stringResource(R.string.home_settings_title),
                leadingIcon = CoreR.drawable.ic_28_chevron_left,
                onLeadingIconClick = goBack,
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(24.dp),
        ) {
            items(
                items = menuItems,
                key = { item -> item.labelRes },
            ) { item ->
                when (item) {
                    is SettingsMenu.OpenUrl -> SettingsMenuItem(
                        text = stringResource(item.labelRes),
                        onClick = { onUrlItemClick(item.menu) },
                    )

                    SettingsMenu.CopyIdentifierCode -> SettingsMenuItem(
                        text = stringResource(item.labelRes),
                        onClick = onCopyIdentifierCodeClick,
                    )

                    SettingsMenu.ContentRecommendation -> SettingsMenuItem(
                        text = stringResource(item.labelRes),
                        onClick = onContentRecommendationClick,
                    )

                    is SettingsMenu.AppVersion -> VersionItem(versionName = item.versionName)
                }
            }
        }
    }
}

@Composable
private fun SettingsMenuItem(
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            style = NDGLTheme.typography.bodyLgRegular,
            color = NDGLTheme.colors.black700,
            modifier = Modifier.weight(1f),
        )
        Icon(
            imageVector = ImageVector.vectorResource(CoreR.drawable.ic_24_chevron_right),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = NDGLTheme.colors.black600,
        )
    }
    HorizontalDivider(color = NDGLTheme.colors.black50)
}

@Composable
private fun VersionItem(
    versionName: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.home_settings_version_info),
            style = NDGLTheme.typography.bodyLgRegular,
            color = NDGLTheme.colors.black700,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = versionName,
            style = NDGLTheme.typography.bodyLgRegular,
            color = NDGLTheme.colors.black400,
        )
    }
    HorizontalDivider(color = NDGLTheme.colors.black50)
}
