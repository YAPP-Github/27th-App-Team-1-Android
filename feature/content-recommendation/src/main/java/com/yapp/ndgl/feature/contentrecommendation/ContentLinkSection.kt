package com.yapp.ndgl.feature.contentrecommendation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.SubcomposeAsyncImage
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.ui.util.launchBrowser
import com.yapp.ndgl.feature.contentrecommendation.ContentRecommendationState.MetadataState
import com.yapp.ndgl.core.ui.R as CoreR

@Composable
internal fun ContentLinkSection(
    contentUrl: String,
    metadataState: MetadataState,
    onUrlChange: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.content_recommendation_link_label),
                style = NDGLTheme.typography.bodyMdSemiBold,
                color = NDGLTheme.colors.black700,
            )
            Icon(
                imageVector = ImageVector.vectorResource(CoreR.drawable.ic_24_asterisk),
                contentDescription = null,
                modifier = Modifier.size(14.dp),
                tint = NDGLTheme.colors.green500,
            )
        }
        UrlInputField(url = contentUrl, onUrlChange = onUrlChange)
        when (metadataState) {
            MetadataState.Empty -> NoLinkHint()
            MetadataState.InvalidUrl -> InvalidUrlError()
            MetadataState.Loading -> ContentPreviewSkeleton()
            is MetadataState.Success -> ContentPreviewSuccess(metadata = metadataState)
            MetadataState.Error -> ContentPreviewError()
        }
    }
}

@Composable
private fun UrlInputField(
    url: String,
    onUrlChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(NDGLTheme.colors.black50)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(CoreR.drawable.ic_24_youtube),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = NDGLTheme.colors.black400,
        )
        BasicTextField(
            value = url,
            onValueChange = onUrlChange,
            modifier = Modifier.weight(1f),
            textStyle = NDGLTheme.typography.bodyMdRegular.copy(color = NDGLTheme.colors.black700),
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Uri,
                imeAction = ImeAction.Done,
            ),
            decorationBox = { innerTextField ->
                if (url.isEmpty()) {
                    Text(
                        text = stringResource(R.string.content_recommendation_link_placeholder),
                        style = NDGLTheme.typography.bodyMdRegular,
                        color = NDGLTheme.colors.black300,
                    )
                }
                innerTextField()
            },
        )
    }
}

@Composable
private fun NoLinkHint() {
    val context = LocalContext.current
    val prefix = stringResource(R.string.content_recommendation_no_link_prefix)
    val channel = stringResource(R.string.content_recommendation_no_link_channel)
    val suffix = stringResource(R.string.content_recommendation_no_link_suffix)
    Text(
        text = buildAnnotatedString {
            append(prefix)
            withLink(
                LinkAnnotation.Clickable(
                    tag = "CHANNEL",
                    styles = TextLinkStyles(
                        style = SpanStyle(color = NDGLTheme.colors.black500, textDecoration = TextDecoration.Underline),
                    ),
                    linkInteractionListener = { context.launchBrowser(BuildConfig.NDGL_INQUIRY_URL) },
                ),
            ) {
                append(channel)
            }
            append(suffix)
        },
        style = NDGLTheme.typography.bodySmRegular.copy(color = NDGLTheme.colors.black400),
    )
}

@Composable
private fun InvalidUrlError() {
    Text(
        text = stringResource(R.string.content_recommendation_invalid_url),
        style = NDGLTheme.typography.bodySmRegular,
        color = NDGLTheme.colors.red500,
    )
}

@Composable
private fun rememberSkeletonAlpha(): Float {
    val infiniteTransition = rememberInfiniteTransition(label = "skeleton")
    return infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 800, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "skeletonAlpha",
    ).value
}

@Composable
private fun ContentPreviewSkeleton() {
    val alpha = rememberSkeletonAlpha()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, NDGLTheme.colors.black50, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(width = 120.dp, height = 80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(NDGLTheme.colors.black100.copy(alpha = alpha)),
        )
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(NDGLTheme.colors.black200.copy(alpha = alpha)),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(20.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(NDGLTheme.colors.black100.copy(alpha = alpha)),
            )
        }
    }
}

@Composable
private fun ContentPreviewSuccess(metadata: MetadataState.Success) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, NDGLTheme.colors.black50, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ThumbnailImage(thumbnailUrl = metadata.thumbnailUrl)
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = metadata.title,
                style = NDGLTheme.typography.bodyMdMedium,
                color = NDGLTheme.colors.black700,
                maxLines = 2,
            )
            Text(
                text = metadata.channelName,
                style = NDGLTheme.typography.bodySmRegular,
                color = NDGLTheme.colors.black400,
                maxLines = 1,
            )
        }
    }
}

@Composable
private fun ThumbnailImage(thumbnailUrl: String) {
    SubcomposeAsyncImage(
        model = thumbnailUrl,
        contentDescription = null,
        modifier = Modifier
            .size(width = 120.dp, height = 80.dp)
            .clip(RoundedCornerShape(12.dp)),
        contentScale = ContentScale.Crop,
        loading = { ThumbnailSkeleton() },
        error = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(NDGLTheme.colors.black50),
            )
        },
    )
}

@Composable
private fun ThumbnailSkeleton() {
    val alpha = rememberSkeletonAlpha()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NDGLTheme.colors.black50.copy(alpha = alpha)),
    )
}

@Composable
private fun ContentPreviewError() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, NDGLTheme.colors.black50, RoundedCornerShape(12.dp))
            .padding(12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(width = 120.dp, height = 80.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(NDGLTheme.colors.black50),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(CoreR.drawable.ic_24_alert),
                contentDescription = null,
                modifier = Modifier.size(28.dp),
                tint = NDGLTheme.colors.black300,
            )
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = stringResource(R.string.content_recommendation_preview_error_title),
                style = NDGLTheme.typography.bodyMdMedium,
                color = NDGLTheme.colors.black700,
            )
            Text(
                text = stringResource(R.string.content_recommendation_preview_error_subtitle),
                style = NDGLTheme.typography.bodySmRegular,
                color = NDGLTheme.colors.black400,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentLinkSectionEmptyPreview() {
    NDGLTheme {
        ContentLinkSection(
            contentUrl = "",
            metadataState = MetadataState.Empty,
            onUrlChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentLinkSectionLoadingPreview() {
    NDGLTheme {
        ContentLinkSection(
            contentUrl = "https://youtube.com/watch?v=example",
            metadataState = MetadataState.Loading,
            onUrlChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentLinkSectionSuccessPreview() {
    NDGLTheme {
        ContentLinkSection(
            contentUrl = "https://youtube.com/watch?v=example",
            metadataState = MetadataState.Success(
                title = "서울 힐링 여행 브이로그 | 경복궁, 북촌한옥마을",
                channelName = "여행유튜버",
                thumbnailUrl = "",
            ),
            onUrlChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ContentLinkSectionErrorPreview() {
    NDGLTheme {
        ContentLinkSection(
            contentUrl = "https://youtube.com/watch?v=example",
            metadataState = MetadataState.Error,
            onUrlChange = {},
        )
    }
}
