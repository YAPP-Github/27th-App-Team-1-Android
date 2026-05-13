package com.yapp.ndgl.feature.home.settings

import androidx.annotation.StringRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.yapp.ndgl.core.base.UiIntent
import com.yapp.ndgl.core.base.UiSideEffect
import com.yapp.ndgl.core.base.UiState
import com.yapp.ndgl.feature.home.R
import com.yapp.ndgl.feature.home.settings.SettingsState.UrlMenu
import kotlinx.collections.immutable.ImmutableList

@Immutable
data class SettingsState(
    val menuItems: ImmutableList<SettingsMenu>,
) : UiState {
    @Stable
    sealed interface SettingsMenu {
        @get:StringRes
        val labelRes: Int

        data class OpenUrl(
            val menu: UrlMenu,
        ) : SettingsMenu {
            override val labelRes = menu.labelRes
        }

        data object CopyIdentifierCode : SettingsMenu {
            override val labelRes = R.string.home_settings_identification_code
        }

        data object ContentRecommendation : SettingsMenu {
            override val labelRes = R.string.home_settings_recommend_link
        }

        data class AppVersion(
            val versionName: String,
        ) : SettingsMenu {
            override val labelRes = R.string.home_settings_version_info
        }
    }

    enum class UrlMenu(
        @get:StringRes val labelRes: Int,
        val url: String,
    ) {
        FAQ(
            labelRes = R.string.home_settings_faq,
            url = "https://repeated-tapir-33f.notion.site/FAQ-30ccbdc5a38380d6af4af7b7c412921e?source=copy_link",
        ),
        TERMS_OF_SERVICE(
            labelRes = R.string.home_settings_terms_of_service,
            url = "https://repeated-tapir-33f.notion.site/2c8cbdc5a3838070a8d8ccdcd0631c9a?source=copy_link",
        ),
        PRIVACY_POLICY(
            labelRes = R.string.home_settings_privacy_policy,
            url = "https://repeated-tapir-33f.notion.site/30ccbdc5a38380e3a50ace64a9b0f398?source=copy_link",
        ),
    }
}

sealed interface SettingsIntent : UiIntent {
    data class ClickUrlMenu(val menu: UrlMenu) : SettingsIntent
    data object ClickCopyIdentifierCodeMenu : SettingsIntent
    data object ClickContentRecommendationMenu : SettingsIntent
}

sealed interface SettingsSideEffect : UiSideEffect {
    data class OpenUrl(val url: String) : SettingsSideEffect
    data class CopyIdentifierCode(val code: String) : SettingsSideEffect
    data object NavigateToContentRecommendation : SettingsSideEffect
}
