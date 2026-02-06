package com.yapp.ndgl.feature.travel.traveldetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.designsystem.NDGLCheckbox
import com.yapp.ndgl.core.ui.theme.NDGLTheme

@Composable
internal fun EditControlBar(
    isAllSelected: Boolean,
    onSelectAllClick: () -> Unit,
    onDeleteSelectedClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        HorizontalDivider(
            thickness = 1.dp,
            color = NDGLTheme.colors.black200.copy(alpha = 0.6f),
        )
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            NDGLCheckbox(
                checked = isAllSelected,
                onClick = onSelectAllClick,
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.select_all),
                color = NDGLTheme.colors.black700,
                style = NDGLTheme.typography.bodyMdMedium,
                modifier = Modifier.clickable {
                    onSelectAllClick()
                },
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = stringResource(R.string.delete_selected),
                color = NDGLTheme.colors.black400,
                style = NDGLTheme.typography.bodyMdMedium,
                modifier = Modifier.clickable {
                    onDeleteSelectedClick()
                },
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = NDGLTheme.colors.black200.copy(alpha = 0.6f),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditControlBarPreview() {
    NDGLTheme {
        EditControlBar(
            isAllSelected = false,
            onSelectAllClick = {},
            onDeleteSelectedClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditControlBarAllSelectedPreview() {
    NDGLTheme {
        EditControlBar(
            isAllSelected = true,
            onSelectAllClick = {},
            onDeleteSelectedClick = {},
        )
    }
}
