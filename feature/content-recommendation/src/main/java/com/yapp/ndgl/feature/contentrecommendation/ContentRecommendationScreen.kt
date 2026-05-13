package com.yapp.ndgl.feature.contentrecommendation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButton
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButtonAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBar
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBarAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLSnackbar
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.feature.contentrecommendation.ContentRecommendationState.MetadataState
import com.yapp.ndgl.feature.contentrecommendation.model.TravelTheme
import kotlinx.collections.immutable.persistentSetOf
import kotlinx.coroutines.launch
import com.yapp.ndgl.core.ui.R as CoreR

@Composable
internal fun ContentRecommendationRoute(
    navigateBack: () -> Unit,
    viewModel: ContentRecommendationViewModel = hiltViewModel(),
) {
    val state by viewModel.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val successMessage = stringResource(R.string.content_recommendation_submit_success)
    val errorMessage = stringResource(R.string.content_recommendation_submit_error)

    ContentRecommendationScreen(
        state = state,
        snackbarHostState = snackbarHostState,
        onUrlChange = { viewModel.onIntent(ContentRecommendationIntent.UpdateUrl(it)) },
        onThemeToggle = { viewModel.onIntent(ContentRecommendationIntent.ToggleTheme(it)) },
        onReasonChange = { viewModel.onIntent(ContentRecommendationIntent.UpdateReason(it)) },
        onSubmit = { viewModel.onIntent(ContentRecommendationIntent.Submit) },
        onBackClick = { viewModel.onIntent(ContentRecommendationIntent.NavigateBack) },
    )

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            ContentRecommendationSideEffect.NavigateBack -> navigateBack()
            ContentRecommendationSideEffect.ShowSubmitSuccess -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(successMessage)
                }
            }

            ContentRecommendationSideEffect.ShowSubmitError -> {
                coroutineScope.launch {
                    snackbarHostState.showSnackbar(errorMessage)
                }
            }
        }
    }
}

@Composable
private fun ContentRecommendationScreen(
    state: ContentRecommendationState,
    snackbarHostState: SnackbarHostState,
    onUrlChange: (String) -> Unit,
    onThemeToggle: (TravelTheme) -> Unit,
    onReasonChange: (String) -> Unit,
    onSubmit: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                NDGLSnackbar(
                    modifier = Modifier.padding(bottom = 100.dp),
                    snackbarData = data,
                )
            }
        },
        topBar = {
            NDGLNavigationBar(
                textAlignType = NDGLNavigationBarAttr.TextAlignType.CENTER,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = NDGLTheme.colors.white)
                    .statusBarsPadding(),
                leadingIcon = CoreR.drawable.ic_28_chevron_left,
                onLeadingIconClick = onBackClick,
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(NDGLTheme.colors.white)
                    .navigationBarsPadding()
                    .padding(horizontal = 24.dp)
                    .padding(top = 16.dp, bottom = 16.dp),
            ) {
                NDGLCTAButton(
                    type = NDGLCTAButtonAttr.Type.PRIMARY,
                    size = NDGLCTAButtonAttr.Size.LARGE,
                    status = if (state.isSubmitEnabled) {
                        NDGLCTAButtonAttr.Status.ACTIVE
                    } else {
                        NDGLCTAButtonAttr.Status.DISABLED
                    },
                    label = stringResource(R.string.content_recommendation_submit_button),
                    onClick = onSubmit,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
                .padding(top = 32.dp),
            verticalArrangement = Arrangement.spacedBy(40.dp),
        ) {
            item {
                HeaderSection()
            }
            item {
                ContentLinkSection(
                    contentUrl = state.contentUrl,
                    metadataState = state.metadataState,
                    onUrlChange = onUrlChange,
                )
            }
            item {
                TravelThemeSection(
                    selectedThemes = state.selectedThemes,
                    onThemeToggle = onThemeToggle,
                )
            }
            item {
                ReasonSection(
                    reason = state.reason,
                    onReasonChange = onReasonChange,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentRecommendationScreenEmptyPreview() {
    NDGLTheme {
        ContentRecommendationScreen(
            state = ContentRecommendationState(),
            snackbarHostState = SnackbarHostState(),
            onUrlChange = {},
            onThemeToggle = {},
            onReasonChange = {},
            onSubmit = {},
            onBackClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentRecommendationScreenLoadingPreview() {
    NDGLTheme {
        ContentRecommendationScreen(
            state = ContentRecommendationState(
                contentUrl = "https://youtube.com/watch?v=example",
                metadataState = MetadataState.Loading,
            ),
            snackbarHostState = SnackbarHostState(),
            onUrlChange = {},
            onThemeToggle = {},
            onReasonChange = {},
            onSubmit = {},
            onBackClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentRecommendationScreenSuccessPreview() {
    NDGLTheme {
        ContentRecommendationScreen(
            state = ContentRecommendationState(
                contentUrl = "https://youtube.com/watch?v=example",
                metadataState = MetadataState.Success(
                    title = "서울 힐링 여행 브이로그 | 경복궁, 북촌한옥마을",
                    channelName = "여행유튜버",
                    thumbnailUrl = "",
                ),
                selectedThemes = persistentSetOf(TravelTheme.HEALING_SCENERY, TravelTheme.LANDMARK),
                reason = "경복궁과 북촌이 너무 예뻐요",
            ),
            snackbarHostState = SnackbarHostState(),
            onUrlChange = {},
            onThemeToggle = {},
            onReasonChange = {},
            onSubmit = {},
            onBackClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentRecommendationScreenErrorPreview() {
    NDGLTheme {
        ContentRecommendationScreen(
            state = ContentRecommendationState(
                contentUrl = "https://youtube.com/watch?v=example",
                metadataState = MetadataState.Error,
            ),
            snackbarHostState = SnackbarHostState(),
            onUrlChange = {},
            onThemeToggle = {},
            onReasonChange = {},
            onSubmit = {},
            onBackClick = {},
        )
    }
}
