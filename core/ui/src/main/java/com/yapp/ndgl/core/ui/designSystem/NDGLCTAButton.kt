package com.yapp.ndgl.core.ui.designSystem

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme

object NDGLCTAButtonAttr {
    enum class Type {
        PRIMARY,
        SECONDARY,
        DESTRUCTIVE,
    }

    enum class Size(
        val height: Dp,
        val horizontalPadding: Dp,
        val horizontalSpacing: Dp,
        val iconSize: Dp,
    ) {
        LARGE(
            height = 56.dp,
            horizontalPadding = 24.dp,
            horizontalSpacing = 8.dp,
            iconSize = 24.dp,
        ),
        MEDIUM(
            height = 40.dp,
            horizontalPadding = 24.dp,
            horizontalSpacing = 8.dp,
            iconSize = 20.dp,
        ),
        SMALL(
            height = 32.dp,
            horizontalPadding = 12.dp,
            horizontalSpacing = 4.dp,
            iconSize = 16.dp,
        ),
    }

    enum class Status {
        ACTIVE,
        DISABLED,
    }
}

@Composable
fun NDGLCTAButton(
    type: NDGLCTAButtonAttr.Type,
    size: NDGLCTAButtonAttr.Size,
    status: NDGLCTAButtonAttr.Status,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    @DrawableRes leadingIcon: Int? = null,
    @DrawableRes trailingIcon: Int? = null,
) {
    val contentColor = type.contentColor(status)

    Row(
        modifier = modifier
            .height(size.height)
            .clip(RoundedCornerShape(8.dp))
            .background(type.containerColor(status))
            .clickable(
                enabled = status != NDGLCTAButtonAttr.Status.DISABLED,
                onClick = onClick,
            )
            .padding(horizontal = size.horizontalPadding, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(
            space = size.horizontalSpacing,
            alignment = Alignment.CenterHorizontally,
        ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leadingIcon?.let { icon ->
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier.size(size.iconSize),
                tint = contentColor,
            )
        }

        Text(
            text = label,
            style = size.labelStyle(),
            color = contentColor,
        )

        trailingIcon?.let { icon ->
            Icon(
                imageVector = ImageVector.vectorResource(icon),
                contentDescription = null,
                modifier = Modifier.size(size.iconSize),
                tint = contentColor,
            )
        }
    }
}

@Composable
private fun NDGLCTAButtonAttr.Type.containerColor(
    status: NDGLCTAButtonAttr.Status,
): Color {
    if (status == NDGLCTAButtonAttr.Status.DISABLED) return NDGLTheme.colors.secondary100
    return when (this) {
        NDGLCTAButtonAttr.Type.PRIMARY -> NDGLTheme.colors.secondary900
        NDGLCTAButtonAttr.Type.SECONDARY -> NDGLTheme.colors.secondary50
        NDGLCTAButtonAttr.Type.DESTRUCTIVE -> NDGLTheme.colors.red50
    }
}

@Composable
private fun NDGLCTAButtonAttr.Type.contentColor(
    status: NDGLCTAButtonAttr.Status,
): Color {
    if (status == NDGLCTAButtonAttr.Status.DISABLED) return NDGLTheme.colors.secondary300
    return when (this) {
        NDGLCTAButtonAttr.Type.PRIMARY -> NDGLTheme.colors.white
        NDGLCTAButtonAttr.Type.SECONDARY -> NDGLTheme.colors.secondary700
        NDGLCTAButtonAttr.Type.DESTRUCTIVE -> NDGLTheme.colors.red500
    }
}

@Composable
private fun NDGLCTAButtonAttr.Size.labelStyle(): TextStyle {
    return when (this) {
        NDGLCTAButtonAttr.Size.LARGE -> NDGLTheme.typography.bodyLgSemiBold
        NDGLCTAButtonAttr.Size.MEDIUM -> NDGLTheme.typography.bodyMdSemiBold
        NDGLCTAButtonAttr.Size.SMALL -> NDGLTheme.typography.bodySmSemiBold
    }
}

@Preview
@Composable
private fun NDGLCTAButtonPrimaryLargePreview() {
    NDGLTheme {
        NDGLCTAButton(
            type = NDGLCTAButtonAttr.Type.PRIMARY,
            size = NDGLCTAButtonAttr.Size.LARGE,
            status = NDGLCTAButtonAttr.Status.ACTIVE,
            label = "Primary Large",
            leadingIcon = R.drawable.ic_24_pin,
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
        )
    }
}

@Preview
@Composable
private fun NDGLCTAButtonSecondaryMediumPreview() {
    NDGLTheme {
        NDGLCTAButton(
            type = NDGLCTAButtonAttr.Type.SECONDARY,
            size = NDGLCTAButtonAttr.Size.MEDIUM,
            status = NDGLCTAButtonAttr.Status.ACTIVE,
            label = "Secondary Medium",
            leadingIcon = R.drawable.ic_20_tv,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun NDGLCTAButtonDestructiveSmallPreview() {
    NDGLTheme {
        NDGLCTAButton(
            type = NDGLCTAButtonAttr.Type.DESTRUCTIVE,
            size = NDGLCTAButtonAttr.Size.SMALL,
            status = NDGLCTAButtonAttr.Status.ACTIVE,
            label = "Destructive Small",
            leadingIcon = R.drawable.ic_20_tv,
            onClick = {},
        )
    }
}

@Preview
@Composable
private fun NDGLCTAButtonDisabledPreview() {
    NDGLTheme {
        NDGLCTAButton(
            type = NDGLCTAButtonAttr.Type.PRIMARY,
            size = NDGLCTAButtonAttr.Size.LARGE,
            status = NDGLCTAButtonAttr.Status.DISABLED,
            label = "Disabled",
            leadingIcon = R.drawable.ic_24_pin,
            onClick = {},
        )
    }
}
