package com.yapp.ndgl.feature.travel.traveldetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButton
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButtonAttr
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.util.toTimeString
import kotlin.math.abs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.hours
import kotlin.time.Duration.Companion.minutes

@Composable
internal fun TimelineContent(
    startTime: Duration,
    endTime: Duration,
    onDismissRequest: () -> Unit,
    onConfirm: (Duration) -> Unit,
) {
    var isSettingStartTime by remember { mutableStateOf(false) }
    var selectedStartTime by remember { mutableStateOf(startTime) }
    var selectedEndTime by remember { mutableStateOf(endTime) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(R.string.timeline_auto_setting_title),
                color = NDGLTheme.colors.black900,
                style = NDGLTheme.typography.titleMdSemiBold,
            )
            Spacer(Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(NDGLTheme.colors.black50)
                    .clickable { onDismissRequest() }
                    .padding(8.dp),
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_24_close),
                    contentDescription = stringResource(R.string.close),
                    tint = NDGLTheme.colors.black900,
                )
            }
        }
        Spacer(Modifier.height(32.dp))

        if (!isSettingStartTime) {
            val hourItems = remember { (0..23).toList() }
            val minuteItems = remember { (0..55 step 5).toList() }

            var selectedHour by remember { mutableIntStateOf(hourItems[0]) }
            var selectedMinute by remember { mutableIntStateOf(minuteItems[0]) }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .align(Alignment.Center)
                        .clip(RoundedCornerShape(8.dp))
                        .background(NDGLTheme.colors.black50),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(48.dp, Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    WheelPicker(
                        items = hourItems,
                        onItemSelected = { selectedHour = it },
                        initialIndex = 12,
                    )
                    WheelPicker(
                        items = minuteItems,
                        onItemSelected = { selectedMinute = it },
                        initialIndex = 0,
                        padEnabled = true,
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
            NDGLCTAButton(
                modifier = Modifier.fillMaxWidth(),
                type = NDGLCTAButtonAttr.Type.PRIMARY,
                size = NDGLCTAButtonAttr.Size.LARGE,
                status = NDGLCTAButtonAttr.Status.ACTIVE,
                label = stringResource(R.string.timeline_save_time),
                onClick = {
                    val totalMinutes = (selectedHour * 60) + selectedMinute
                    selectedStartTime = totalMinutes.minutes
                    selectedEndTime = selectedStartTime + 15.hours
                    isSettingStartTime = false
                },
            )
        } else {
            val isEndTimeExceeds24Hours = selectedEndTime.inWholeHours >= 24
            val isTimeSet = selectedStartTime.inWholeHours > 0 && selectedEndTime.inWholeHours > 0

            Text(
                text = stringResource(R.string.timeline_start_time),
                color = NDGLTheme.colors.black700,
                style = NDGLTheme.typography.bodyMdMedium,
            )
            Spacer(Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(NDGLTheme.colors.black50)
                    .clickable { isSettingStartTime = true }
                    .padding(horizontal = 24.dp, vertical = 17.5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selectedStartTime.toTimeString(),
                    color = NDGLTheme.colors.black400,
                    style = NDGLTheme.typography.bodyMdMedium,
                )
                Spacer(Modifier.weight(1f))
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_24_chevron_right),
                    contentDescription = null,
                    tint = NDGLTheme.colors.black400,
                )
            }
            Spacer(Modifier.height(20.dp))
            Text(
                text = stringResource(R.string.timeline_end_time),
                color = NDGLTheme.colors.black700,
                style = NDGLTheme.typography.bodyMdMedium,
            )
            Spacer(Modifier.height(4.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(NDGLTheme.colors.black50)
                    .padding(horizontal = 24.dp, vertical = 17.5.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = selectedEndTime.toTimeString(),
                    color = NDGLTheme.colors.black400,
                    style = NDGLTheme.typography.bodyMdMedium,
                )
            }

            if (isEndTimeExceeds24Hours) {
                Spacer(Modifier.height(4.dp))
                Text(
                    text = stringResource(R.string.timeline_time_exceeds_warning),
                    color = NDGLTheme.colors.red500,
                    style = NDGLTheme.typography.bodySmMedium,
                )
            }
            Spacer(Modifier.height(32.dp))
            NDGLCTAButton(
                modifier = Modifier.fillMaxWidth(),
                type = NDGLCTAButtonAttr.Type.PRIMARY,
                size = NDGLCTAButtonAttr.Size.LARGE,
                status = if (isTimeSet && !isEndTimeExceeds24Hours) {
                    NDGLCTAButtonAttr.Status.ACTIVE
                } else {
                    NDGLCTAButtonAttr.Status.DISABLED
                },
                label = stringResource(R.string.timeline_set_time),
                onClick = {
                    onConfirm(selectedStartTime)
                },
            )
        }
    }
}

@Composable
private fun <T> WheelPicker(
    modifier: Modifier = Modifier,
    items: List<T>,
    onItemSelected: (T) -> Unit,
    initialIndex: Int = 0,
    itemHeight: Dp = 40.dp,
    visibleItemCount: Int = 5,
    padEnabled: Boolean = false,
) {
    val totalItemsCount = Int.MAX_VALUE
    val startIndex = (totalItemsCount / 2) - (totalItemsCount / 2 % items.size) + initialIndex

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = startIndex)
    val snapFlingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
    val currentIndex by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex + (visibleItemCount / 2)
        }
    }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPostScroll(
                consumed: Offset,
                available: Offset,
                source: NestedScrollSource,
            ): Offset {
                return available
            }
        }
    }

    LaunchedEffect(currentIndex) {
        onItemSelected(items[currentIndex % items.size])
    }

    Box(
        modifier = modifier
            .height(itemHeight * visibleItemCount)
            .nestedScroll(nestedScrollConnection),
        contentAlignment = Alignment.Center,
    ) {
        LazyColumn(
            state = listState,
            flingBehavior = snapFlingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(totalItemsCount) { index ->
                val actualIndex = index % items.size
                val item = items[actualIndex]
                val distance = abs(index - currentIndex)

                val scale = when (distance) {
                    0 -> 1f
                    1 -> 0.85f
                    else -> 0.60f
                }
                val alpha = when (distance) {
                    0 -> 1f
                    else -> 0.35f
                }

                Box(
                    modifier = Modifier
                        .height(itemHeight),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = if (padEnabled) item.toString().padStart(2, '0') else item.toString(),
                        style = NDGLTheme.typography.bodyMdRegular.copy(fontSize = 22.sp),
                        color = NDGLTheme.colors.black900.copy(alpha = alpha),
                        modifier = Modifier.graphicsLayer {
                            scaleX = scale
                            scaleY = scale
                        },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TimelineContentPreview() {
    NDGLTheme {
        TimelineContent(
            startTime = 9.hours,
            endTime = 25.hours,
            onDismissRequest = {},
            onConfirm = {},
        )
    }
}
