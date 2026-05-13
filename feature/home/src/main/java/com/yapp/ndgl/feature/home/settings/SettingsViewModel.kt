package com.yapp.ndgl.feature.home.settings

import androidx.lifecycle.viewModelScope
import com.yapp.ndgl.core.base.BaseViewModel
import com.yapp.ndgl.data.auth.repository.AuthRepository
import com.yapp.ndgl.feature.home.settings.SettingsState.SettingsMenu
import com.yapp.ndgl.feature.home.util.AppVersionProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val appVersionProvider: AppVersionProvider,
    private val authRepository: AuthRepository,
) : BaseViewModel<SettingsState, SettingsIntent, SettingsSideEffect>(
    initialState = SettingsState(
        menuItems = persistentListOf(
            SettingsMenu.OpenUrl(SettingsState.UrlMenu.FAQ),
            SettingsMenu.ContentRecommendation,
            SettingsMenu.CopyIdentifierCode,
            SettingsMenu.OpenUrl(SettingsState.UrlMenu.TERMS_OF_SERVICE),
            SettingsMenu.OpenUrl(SettingsState.UrlMenu.PRIVACY_POLICY),
            SettingsMenu.AppVersion(appVersionProvider.getAppVersion()),
        ),
    ),
) {
    override suspend fun handleIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.ClickUrlMenu -> postOpenUrl(intent.menu)
            SettingsIntent.ClickCopyIdentifierCodeMenu -> postCopyIdentifierCode()
            SettingsIntent.ClickContentRecommendationMenu -> {
                postSideEffect(SettingsSideEffect.NavigateToContentRecommendation)
            }
        }
    }

    private fun postOpenUrl(menu: SettingsState.UrlMenu) {
        postSideEffect(SettingsSideEffect.OpenUrl(menu.url))
    }

    private fun postCopyIdentifierCode() {
        viewModelScope.launch {
            val uuid = authRepository.getIdentifierCode()
            postSideEffect(SettingsSideEffect.CopyIdentifierCode(uuid))
        }
    }
}
