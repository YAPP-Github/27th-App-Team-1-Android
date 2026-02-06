package com.yapp.ndgl.feature.travel.followtravel.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.util.formatString
import com.yapp.ndgl.feature.travel.followtravel.TransportSegment
import com.yapp.ndgl.feature.travel.followtravel.TransportType
import kotlin.time.Duration.Companion.minutes

@Composable
internal fun TransportSegment(
    segment: TransportSegment,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(Modifier.width(7.dp))
        Icon(imageVector = ImageVector.vectorResource(segment.type.iconRes), contentDescription = null, tint = Color.Unspecified)
        Spacer(Modifier.width(8.dp))
        Text(
            text = stringResource(
                R.string.transport_segment_format,
                segment.duration.formatString(),
                segment.formatDistance(),
            ),
            color = NDGLTheme.colors.black400,
            style = NDGLTheme.typography.bodyMdRegular,
        )
        Spacer(Modifier.width(4.dp))
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_20_chevron_right),
            contentDescription = null,
            tint = NDGLTheme.colors.black400,
        )
    }
}

@Preview
@Composable
private fun TransportSegmentPreview() {
    NDGLTheme {
        TransportSegment(
            segment = TransportSegment(
                type = TransportType.WALK,
                duration = 15.minutes,
                distance = 1200,
            ),
        )
    }
}
