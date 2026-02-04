package com.yapp.ndgl.feature.travel.datepicker

import com.yapp.ndgl.core.base.BaseViewModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.datetime.LocalDate

@HiltViewModel(assistedFactory = DatePickerViewModel.Factory::class)
class DatePickerViewModel @AssistedInject constructor(
    @Assisted private val tripDays: Int,
) : BaseViewModel<DatePickerState, DatePickerIntent, DatePickerSideEffect>(
    initialState = DatePickerState(tripDays = tripDays),
) {
    override suspend fun handleIntent(intent: DatePickerIntent) {
        when (intent) {
            is DatePickerIntent.SelectDate -> selectDate(intent.date)
            is DatePickerIntent.SelectPreviousMonth -> selectPreviousMonth()
            is DatePickerIntent.SelectNextMonth -> selectNextMonth()
            is DatePickerIntent.SelectYearMonth -> selectYearMonth(intent.year, intent.month)
            is DatePickerIntent.ClickCompleteButton -> clickCompleteButton()
            is DatePickerIntent.DismissDialog -> dismissDialog()
            is DatePickerIntent.ClickViewTravelButton -> clickViewTravelButton()
        }
    }

    private fun selectDate(date: LocalDate) {
        reduce {
            when {
                startDate == null -> {
                    copy(
                        startDate = date,
                        endDate = null,
                        isSelectingRange = true,
                    )
                }

                isSelectingRange -> {
                    if (date == startDate) {
                        copy(startDate = null, isSelectingRange = false)
                    } else if (date < startDate) {
                        copy(
                            startDate = date,
                            endDate = startDate,
                            isSelectingRange = false,
                        )
                    } else {
                        copy(
                            endDate = date,
                            isSelectingRange = false,
                        )
                    }
                }

                else -> {
                    copy(
                        startDate = date,
                        endDate = null,
                        isSelectingRange = true,
                    )
                }
            }
        }
    }

    private fun selectPreviousMonth() {
        reduce {
            val newMonth = if (currentMonth == 1) 12 else currentMonth - 1
            val newYear = if (currentMonth == 1) currentYear - 1 else currentYear
            copy(currentYear = newYear, currentMonth = newMonth)
        }
    }

    private fun selectNextMonth() {
        reduce {
            val newMonth = if (currentMonth == 12) 1 else currentMonth + 1
            val newYear = if (currentMonth == 12) currentYear + 1 else currentYear
            copy(currentYear = newYear, currentMonth = newMonth)
        }
    }

    private fun selectYearMonth(year: Int, month: Int) {
        reduce { copy(currentYear = year, currentMonth = month) }
    }

    private fun clickCompleteButton() {
        val startDate = state.value.startDate
        val endDate = state.value.endDate

        if (startDate != null && endDate != null) {
            reduce { copy(showDialog = true) }
        }
    }

    private fun dismissDialog() {
        reduce { copy(showDialog = false) }
    }

    private fun clickViewTravelButton() {
        val startDate = state.value.startDate
        val endDate = state.value.endDate

        if (startDate != null && endDate != null) {
            postSideEffect(DatePickerSideEffect.NavigateToTravelDetail)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted tripDays: Int): DatePickerViewModel
    }
}
