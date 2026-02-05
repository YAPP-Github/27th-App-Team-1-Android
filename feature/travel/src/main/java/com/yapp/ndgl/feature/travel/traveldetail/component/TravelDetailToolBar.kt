package com.yapp.ndgl.feature.travel.traveldetail.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme

@Composable
internal fun TravelDetailToolBar(
    clickTimelineAutoSetting: () -> Unit,
    clickEditTravel: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TimelineAutoSettingButton(onClick = clickTimelineAutoSetting)
        Spacer(modifier = Modifier.width(16.dp))
        EditTravelButton(onClick = clickEditTravel)
    }
}

@Composable
private fun TimelineAutoSettingButton(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.wrapContentWidth().clickable {
            onClick()
        },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.timeline_auto_setting),
            color = NDGLTheme.colors.black400,
            style = NDGLTheme.typography.bodyMdMedium,
        )
        Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_20_refresh), contentDescription = null)
    }
}

@Composable
private fun EditTravelButton(
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier.wrapContentWidth().clickable {
            onClick()
        },
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.edit_travel),
            color = NDGLTheme.colors.black400,
            style = NDGLTheme.typography.bodyMdMedium,
        )
        Icon(imageVector = ImageVector.vectorResource(R.drawable.ic_20_edit), contentDescription = null)
    }
}
