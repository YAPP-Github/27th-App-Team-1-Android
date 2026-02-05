package com.yapp.ndgl.core.ui.designsystem

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme

@Composable
fun NDGLCheckbox(
    checked: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(
                if (checked) NDGLTheme.colors.green500 else NDGLTheme.colors.white,
            )
            .border(
                width = 1.25.dp,
                color = if (checked) Color.Unspecified else NDGLTheme.colors.black400,
                shape = RoundedCornerShape(4.dp),
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {
        if (checked) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_20_check),
                contentDescription = null,
                tint = NDGLTheme.colors.white,
            )
        }
    }
}

@Preview
@Composable
private fun NDGLCheckboxPreview() {
    NDGLTheme {
        NDGLCheckbox(
            checked = false,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun NDGLCheckboxCheckedPreview() {
    NDGLTheme {
        NDGLCheckbox(
            checked = true,
            onClick = {},
        )
    }
}
