package com.yapp.ndgl.core.ui.designSystem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

object NDGLChipTabAttr {
    data class Tab(
        val tag: String,
        val name: String,
    )
}

@Composable
fun NDGLChipTab(
    tabs: PersistentList<NDGLChipTabAttr.Tab>,
    selectedIndex: Int,
    onTabSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier.horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        tabs.forEachIndexed { index, tab ->
            NDGLChipTabItem(
                isSelected = index == selectedIndex,
                name = tab.name,
                onTabSelected = { onTabSelected(index) },
            )
        }
    }
}

@Composable
private fun NDGLChipTabItem(
    isSelected: Boolean,
    name: String,
    onTabSelected: () -> Unit,
) {
    Box(
        modifier = Modifier
            .width(72.dp)
            .clip(CircleShape)
            .chipStyle(isSelected)
            .clickable(onClick = onTabSelected)
            .padding(horizontal = 14.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = name,
            style = NDGLTheme.typography.bodyMdMedium,
            color = if (isSelected) {
                NDGLTheme.colors.white
            } else {
                NDGLTheme.colors.secondary400
            },
        )
    }
}

@Composable
private fun Modifier.chipStyle(
    isSelected: Boolean,
): Modifier = this.then(
    if (isSelected) {
        Modifier.background(NDGLTheme.colors.secondary700, CircleShape)
    } else {
        Modifier
            .background(NDGLTheme.colors.white, CircleShape)
            .border(
                width = 1.dp,
                color = NDGLTheme.colors.secondary200,
                shape = CircleShape,
            )
    },
)

@Preview(showBackground = true)
@Composable
private fun NDGLChipTabPreview() {
    NDGLTheme {
        var selectedIndex by remember { mutableIntStateOf(0) }

        NDGLChipTab(
            tabs = persistentListOf(
                NDGLChipTabAttr.Tab(
                    tag = "1",
                    name = "1일차",
                ),
                NDGLChipTabAttr.Tab(
                    tag = "1",
                    name = "2일차",
                ),
                NDGLChipTabAttr.Tab(
                    tag = "1",
                    name = "3일차",
                ),
            ),
            selectedIndex = selectedIndex,
            onTabSelected = { selectedIndex = it },
        )
    }
}
