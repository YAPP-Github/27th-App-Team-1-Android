package com.yapp.ndgl.core.ui.designSystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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

object NDGLOutlinedButtonAttr {
    enum class Status {
        ACTIVE,
        DISABLED,
    }
}

@Composable
fun NDGLOutlinedButton(
    status: NDGLOutlinedButtonAttr.Status,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
) {
    val contentColor = status.contentColor()

    Row(
        modifier = modifier
            .wrapContentWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(status.containerColor())
            .border(
                width = 1.dp,
                color = NDGLTheme.colors.secondary200,
                shape = RoundedCornerShape(8.dp),
            )
            .clickable(
                enabled = status != NDGLOutlinedButtonAttr.Status.DISABLED,
                onClick = onClick,
            )
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIcon?.let { icon ->
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = contentColor,
            )
        }

        Text(
            text = label,
            style = NDGLTheme.typography.bodyMdSemiBold,
            color = contentColor,
        )

        trailingIcon?.let { icon ->
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = contentColor,
            )
        }
    }
}

@Composable
private fun NDGLOutlinedButtonAttr.Status.containerColor(): Color {
    return when (this) {
        NDGLOutlinedButtonAttr.Status.ACTIVE -> NDGLTheme.colors.white
        NDGLOutlinedButtonAttr.Status.DISABLED -> NDGLTheme.colors.secondary300
    }
}

@Composable
private fun NDGLOutlinedButtonAttr.Status.contentColor(): Color {
    return when (this) {
        NDGLOutlinedButtonAttr.Status.ACTIVE -> NDGLTheme.colors.secondary600
        NDGLOutlinedButtonAttr.Status.DISABLED -> NDGLTheme.colors.secondary400
    }
}

@Preview
@Composable
private fun NDGLOutlinedButtonActivePreview() {
    NDGLTheme {
        NDGLOutlinedButton(
            status = NDGLOutlinedButtonAttr.Status.ACTIVE,
            label = "Active",
            leadingIcon = R.drawable.ic_20_tv,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun NDGLOutlinedButtonDisabledPreview() {
    NDGLTheme {
        NDGLOutlinedButton(
            status = NDGLOutlinedButtonAttr.Status.DISABLED,
            label = "Disabled",
            leadingIcon = R.drawable.ic_20_tv,
            onClick = {},
        )
    }
}
