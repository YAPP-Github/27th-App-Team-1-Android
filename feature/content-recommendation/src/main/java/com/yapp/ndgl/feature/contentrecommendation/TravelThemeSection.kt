package com.yapp.ndgl.feature.contentrecommendation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.feature.contentrecommendation.model.TravelTheme
import kotlinx.collections.immutable.persistentSetOf
import com.yapp.ndgl.core.ui.R as CoreR

@Composable
internal fun TravelThemeSection(
    selectedThemes: Set<TravelTheme>,
    onThemeToggle: (TravelTheme) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.content_recommendation_theme_label),
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
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            TravelTheme.entries.forEach { theme ->
                TravelThemeChip(
                    theme = theme,
                    isSelected = selectedThemes.contains(theme),
                    onToggle = { onThemeToggle(theme) },
                )
            }
        }
    }
}

@Composable
private fun TravelThemeChip(
    theme: TravelTheme,
    isSelected: Boolean,
    onToggle: () -> Unit,
) {
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = if (isSelected) Color.Transparent else NDGLTheme.colors.black200,
                shape = CircleShape,
            )
            .clip(CircleShape)
            .background(if (isSelected) NDGLTheme.colors.black900 else NDGLTheme.colors.white)
            .clickable(onClick = onToggle)
            .padding(horizontal = 14.dp, vertical = 6.dp),
    ) {
        Text(
            text = theme.displayName,
            style = NDGLTheme.typography.bodyMdMedium,
            color = if (isSelected) NDGLTheme.colors.white else NDGLTheme.colors.black400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelThemeSectionPreview() {
    NDGLTheme {
        TravelThemeSection(
            selectedThemes = persistentSetOf(TravelTheme.FOOD, TravelTheme.CAFE_DESSERT),
            onThemeToggle = {},
        )
    }
}
