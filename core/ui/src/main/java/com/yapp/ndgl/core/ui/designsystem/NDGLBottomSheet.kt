package com.yapp.ndgl.core.ui.designsystem

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.theme.NDGLTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NDGLBottomSheet(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    showDragHandle: Boolean = true,
    content: @Composable ColumnScope.() -> Unit,
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        dragHandle = if (showDragHandle) {
            { BottomSheetDefaults.DragHandle() }
        } else {
            null
        },
        containerColor = NDGLTheme.colors.white,
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        content = content,
    )
}

@Preview(showBackground = true)
@Composable
private fun NDGLBottomSheetWithHandlePreview() {
    NDGLTheme {
        NDGLBottomSheet(
            onDismissRequest = {},
            showDragHandle = true,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            ) {
                Text(
                    text = "바텀시트 제목",
                    style = NDGLTheme.typography.subtitleLgSemiBold,
                    color = NDGLTheme.colors.black900,
                )
                Text(
                    text = "바텀시트 내용입니다.",
                    modifier = Modifier.padding(top = 16.dp),
                    style = NDGLTheme.typography.bodyLgMedium,
                    color = NDGLTheme.colors.black500,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun NDGLBottomSheetWithoutHandlePreview() {
    NDGLTheme {
        NDGLBottomSheet(
            onDismissRequest = {},
            showDragHandle = false,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
            ) {
                Text(
                    text = "바텀시트 제목",
                    style = NDGLTheme.typography.subtitleLgSemiBold,
                    color = NDGLTheme.colors.black900,
                )
                Text(
                    text = "바텀시트 내용입니다.",
                    modifier = Modifier.padding(top = 16.dp),
                    style = NDGLTheme.typography.bodyLgMedium,
                    color = NDGLTheme.colors.black500,
                )
            }
        }
    }
}
