package com.yapp.ndgl.core.ui.designSystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

object NDGLNavigationBarAttr {
    enum class TextAlignType {
        START,
        CENTER,
    }
}

@Composable
fun NDGLNavigationBar(
    textAlignType: NDGLNavigationBarAttr.TextAlignType,
    modifier: Modifier = Modifier,
    headline: String? = null,
    @DrawableRes leadingIcon: Int? = null,
    onLeadingIconClick: () -> Unit = {},
    @DrawableRes trailingIcons: ImmutableList<Int>? = null,
    onTrailingIconClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 24.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIcon?.let { icon ->
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onLeadingIconClick),
                tint = NDGLTheme.colors.secondary700,
            )
        }

        headline?.let { text ->
            Text(
                text = text,
                modifier = Modifier.weight(1f),
                style = NDGLTheme.typography.bodyLgMedium,
                color = NDGLTheme.colors.secondary700,
                textAlign = when (textAlignType) {
                    NDGLNavigationBarAttr.TextAlignType.START -> TextAlign.Start
                    NDGLNavigationBarAttr.TextAlignType.CENTER -> TextAlign.Center
                },
            )
        } ?: Spacer(modifier = Modifier.weight(1f))

        trailingIcons?.forEachIndexed { index, icon ->
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .clickable(onClick = onTrailingIconClick),
                tint = NDGLTheme.colors.secondary700,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NDGLNavigationBarCenterPreview() {
    NDGLTheme {
        NDGLNavigationBar(
            textAlignType = NDGLNavigationBarAttr.TextAlignType.CENTER,
            headline = "미리보기",
            leadingIcon = R.drawable.ic_28_chevron_left,
            trailingIcons = persistentListOf(R.drawable.ic_28_search, R.drawable.ic_28_settings),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NDGLNavigationBarStartPreview() {
    NDGLTheme {
        NDGLNavigationBar(
            textAlignType = NDGLNavigationBarAttr.TextAlignType.START,
            headline = "미리보기",
            leadingIcon = R.drawable.ic_28_chevron_left,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun NDGLNavigationBarNoHeadlinePreview() {
    NDGLTheme {
        NDGLNavigationBar(
            textAlignType = NDGLNavigationBarAttr.TextAlignType.START,
            leadingIcon = R.drawable.ic_28_menu,
            trailingIcons = persistentListOf(R.drawable.ic_28_search),
        )
    }
}
