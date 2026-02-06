package com.yapp.ndgl.feature.travel.datepicker.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.ui.util.dropShadow
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.minus
import kotlinx.datetime.plus

@Composable
internal fun CalendarView(
    year: Int,
    month: Int,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        CalendarHeader(
            year = year,
            month = month,
            onPreviousMonth = onPreviousMonth,
            onNextMonth = onNextMonth,
        )
        Spacer(Modifier.height(16.dp))
        CalendarBody(
            year = year,
            month = month,
            startDate = startDate,
            endDate = endDate,
            onDateSelected = onDateSelected,
        )
    }
}

@Composable
private fun CalendarHeader(
    year: Int,
    month: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(
                    id = R.string.date_picker_year_month_format,
                    year,
                    month,
                ),
                style = NDGLTheme.typography.subtitleLgSemiBold,
                color = NDGLTheme.colors.black900,
            )
            Spacer(Modifier.width(4.dp))
            Icon(
                painter = painterResource(R.drawable.ic_24_smarrow_down),
                contentDescription = null,
            )
            Spacer(Modifier.weight(1f))
            Icon(
                painter = painterResource(R.drawable.ic_24_chevron_left),
                contentDescription = "Previous Month",
                modifier = Modifier.clickable(onClick = onPreviousMonth),
                tint = NDGLTheme.colors.black400,
            )
            Spacer(Modifier.width(16.dp))
            Icon(
                painter = painterResource(R.drawable.ic_24_chevron_right),
                contentDescription = "Next Month",
                modifier = Modifier.clickable(onClick = onNextMonth),
                tint = NDGLTheme.colors.black400,
            )
        }
    }
}

@Composable
private fun CalendarBody(
    year: Int,
    month: Int,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
) {
    val daysOfWeek = listOf(
        DayOfWeek.SUNDAY,
        DayOfWeek.MONDAY,
        DayOfWeek.TUESDAY,
        DayOfWeek.WEDNESDAY,
        DayOfWeek.THURSDAY,
        DayOfWeek.FRIDAY,
        DayOfWeek.SATURDAY,
    )

    val allDays = remember(year, month) {
        buildCalendarDays(year, month)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier = Modifier.fillMaxWidth(),
    ) {
        items(daysOfWeek) { dayOfWeek ->
            Box(
                modifier = Modifier
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = dayOfWeek.name.take(3).lowercase().replaceFirstChar { it.uppercase() },
                    style = NDGLTheme.typography.bodyMdMedium,
                    color = if (dayOfWeek == DayOfWeek.SUNDAY) NDGLTheme.colors.red500 else NDGLTheme.colors.black500,
                )
            }
        }

        items(allDays) { dayInfo ->
            CalendarDay(
                dayInfo = dayInfo,
                startDate = startDate,
                endDate = endDate,
                onDateSelected = onDateSelected,
            )
        }
    }
}

@Composable
private fun CalendarDay(
    dayInfo: DayInfo,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit,
) {
    val date = dayInfo.date
    val monthType = dayInfo.monthType

    val isStartDate = date == startDate
    val isEndDate = date == endDate
    val isInRange = startDate != null && endDate != null && date > startDate && date < endDate
    val isSelected = isStartDate || isEndDate
    val isSunday = date.dayOfWeek == DayOfWeek.SUNDAY

    Box(
        modifier = Modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center,
    ) {
        if (isInRange || isStartDate || isEndDate) {
            Row(modifier = Modifier.matchParentSize()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = if (isInRange || (isEndDate && startDate != null)) {
                                NDGLTheme.colors.green200
                            } else {
                                Color.Transparent
                            },
                        ),
                )
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .background(
                            color = if (isInRange || (isStartDate && endDate != null)) {
                                NDGLTheme.colors.green200
                            } else {
                                Color.Transparent
                            },
                        ),
                )
            }
        }
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 2.dp),
            contentAlignment = Alignment.Center,
        ) {
            if (isStartDate && endDate != null) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(34.dp))
                        .dropShadow(
                            shape = RoundedCornerShape(34.dp),
                            color = Color(0xFF28A745).copy(alpha = 0.4f),
                            blur = 4.dp,
                        )
                        .background(
                            NDGLTheme.colors.green500,
                            shape = CircleShape,
                        ),
                )
            }

            if (isEndDate && startDate != null) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .clip(RoundedCornerShape(34.dp))
                        .dropShadow(
                            shape = RoundedCornerShape(34.dp),
                            color = Color(0xFF28A745).copy(alpha = 0.4f),
                            blur = 4.dp,
                        )
                        .background(
                            NDGLTheme.colors.green500,
                            shape = CircleShape,
                        ),
                )
            }
        }

        Box(
            modifier = Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(34.dp))
                .background(
                    color = if (isSelected) NDGLTheme.colors.green500 else Color.Transparent,
                )
                .clickable { onDateSelected(date) },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = NDGLTheme.typography.bodyMdMedium,
                color = when {
                    monthType == MonthType.PREVIOUS && !isSunday -> NDGLTheme.colors.black400.copy(alpha = 0.3f)
                    monthType == MonthType.PREVIOUS && isSunday -> NDGLTheme.colors.red500.copy(alpha = 0.3f)
                    monthType == MonthType.NEXT -> NDGLTheme.colors.black400
                    isSelected -> NDGLTheme.colors.white
                    isSunday -> NDGLTheme.colors.red500
                    else -> NDGLTheme.colors.black700
                },
            )
        }
    }
}

enum class MonthType {
    PREVIOUS,
    CURRENT,
    NEXT,
}

private data class DayInfo(
    val date: LocalDate,
    val monthType: MonthType,
)

private fun buildCalendarDays(year: Int, month: Int): List<DayInfo> {
    val firstDayOfMonth = LocalDate(year, month, 1)
    val daysInMonth = firstDayOfMonth
        .plus(1, DateTimeUnit.MONTH)
        .minus(1, DateTimeUnit.DAY)
        .dayOfMonth

    val firstDayOfWeek = (firstDayOfMonth.dayOfWeek.ordinal + 1) % 7

    val days = mutableListOf<DayInfo>()

    // Previous month days
    if (firstDayOfWeek > 0) {
        val prevMonth = if (month == 1) 12 else month - 1
        val prevYear = if (month == 1) year - 1 else year
        val daysInPrevMonth = firstDayOfMonth.minus(1, DateTimeUnit.DAY).dayOfMonth

        for (i in (daysInPrevMonth - firstDayOfWeek + 1)..daysInPrevMonth) {
            days.add(DayInfo(LocalDate(prevYear, prevMonth, i), MonthType.PREVIOUS))
        }
    }

    // Current month days
    for (day in 1..daysInMonth) {
        days.add(DayInfo(LocalDate(year, month, day), MonthType.CURRENT))
    }

    // Next month days
    val remainingInWeek = (7 - (days.size % 7)) % 7
    if (remainingInWeek > 0) {
        val nextMonth = if (month == 12) 1 else month + 1
        val nextYear = if (month == 12) year + 1 else year

        for (day in 1..remainingInWeek) {
            days.add(DayInfo(LocalDate(nextYear, nextMonth, day), MonthType.NEXT))
        }
    }

    return days
}

@Preview(showBackground = true)
@Composable
private fun CalendarViewPreview() {
    NDGLTheme {
        CalendarView(
            year = 2026,
            month = 4,
            startDate = LocalDate(2026, 4, 23),
            endDate = LocalDate(2026, 4, 27),
            onDateSelected = {},
            onPreviousMonth = {},
            onNextMonth = {},
        )
    }
}
