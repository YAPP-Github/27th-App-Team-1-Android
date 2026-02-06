package com.yapp.ndgl.feature.travel.datepicker

import com.yapp.ndgl.core.base.UiIntent
import com.yapp.ndgl.core.base.UiSideEffect
import com.yapp.ndgl.core.base.UiState
import kotlinx.datetime.Clock.System.now
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone.Companion.currentSystemDefault
import kotlinx.datetime.toLocalDateTime

data class DatePickerState(
    val tripDays: Int,
    private val initialDateTime: LocalDateTime = now().toLocalDateTime(currentSystemDefault()),
    val currentYear: Int = initialDateTime.year,
    val currentMonth: Int = initialDateTime.monthNumber,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val isSelectingRange: Boolean = false,
    val showDialog: Boolean = false,
) : UiState {
    val isDateSelected: Boolean
        get() = startDate != null && endDate != null

    val isInsufficientDuration: Boolean
        get() = if (startDate != null && endDate != null) {
            (endDate.toEpochDays() - startDate.toEpochDays() + 1) < tripDays
        } else {
            false
        }
}

sealed interface DatePickerIntent : UiIntent {
    data class SelectDate(val date: LocalDate) : DatePickerIntent
    data object SelectPreviousMonth : DatePickerIntent
    data object SelectNextMonth : DatePickerIntent
    data class SelectYearMonth(val year: Int, val month: Int) : DatePickerIntent
    data object ClickCompleteButton : DatePickerIntent
    data object DismissDialog : DatePickerIntent
    data object ClickViewTravelButton : DatePickerIntent
}

sealed interface DatePickerSideEffect : UiSideEffect {
    // TODO 실제 로직으로 변경
    data object NavigateToTravelDetail : DatePickerSideEffect
}
