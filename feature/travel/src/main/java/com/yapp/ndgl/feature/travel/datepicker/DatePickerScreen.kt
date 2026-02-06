package com.yapp.ndgl.feature.travel.datepicker

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButton
import com.yapp.ndgl.core.ui.designsystem.NDGLCTAButtonAttr
import com.yapp.ndgl.core.ui.designsystem.NDGLModal
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBar
import com.yapp.ndgl.core.ui.designsystem.NDGLNavigationBarAttr
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.feature.travel.datepicker.component.CalendarView
import kotlinx.datetime.LocalDate

@Composable
internal fun DatePickerRoute(
    viewModel: DatePickerViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
    innerPadding: PaddingValues,
) {
    val state by viewModel.collectAsState()

    fun selectDate(date: LocalDate) {
        viewModel.onIntent(DatePickerIntent.SelectDate(date))
    }

    fun selectPreviousMonth() {
        viewModel.onIntent(DatePickerIntent.SelectPreviousMonth)
    }

    fun selectNextMonth() {
        viewModel.onIntent(DatePickerIntent.SelectNextMonth)
    }

    fun clickCompleteButton() {
        viewModel.onIntent(DatePickerIntent.ClickCompleteButton)
    }

    fun dismissDialog() {
        viewModel.onIntent(DatePickerIntent.DismissDialog)
    }

    fun clickTravelButton() {
        viewModel.onIntent(DatePickerIntent.ClickViewTravelButton)
    }

    DatePickerScreen(
        state = state,
        selectDate = ::selectDate,
        selectPreviousMonth = ::selectPreviousMonth,
        selectNextMonth = ::selectNextMonth,
        clickCompleteButton = ::clickCompleteButton,
        clickBackButton = navigateBack,
        dismissDialog = ::dismissDialog,
        clickTravelButton = ::clickTravelButton,
        innerPadding = innerPadding,
    )

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is DatePickerSideEffect.NavigateToTravelDetail -> {
                // TODO
            }
        }
    }
}

@Composable
private fun DatePickerScreen(
    state: DatePickerState,
    selectDate: (LocalDate) -> Unit,
    selectPreviousMonth: () -> Unit,
    selectNextMonth: () -> Unit,
    clickCompleteButton: () -> Unit,
    clickBackButton: () -> Unit,
    dismissDialog: () -> Unit,
    clickTravelButton: () -> Unit,
    innerPadding: PaddingValues,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NDGLTheme.colors.white)
            .padding(innerPadding),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp),
        ) {
            NDGLNavigationBar(
                modifier = Modifier.fillMaxWidth(),
                headline = stringResource(R.string.date_picker_title),
                textAlignType = NDGLNavigationBarAttr.TextAlignType.CENTER,
                leadingIcon = R.drawable.ic_28_chevron_left,
                onLeadingIconClick = clickBackButton,
            )
            Spacer(Modifier.height(24.dp))
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
            ) {
                CalendarView(
                    year = state.currentYear,
                    month = state.currentMonth,
                    startDate = state.startDate,
                    endDate = state.endDate,
                    onDateSelected = selectDate,
                    onPreviousMonth = selectPreviousMonth,
                    onNextMonth = selectNextMonth,
                )

                if (state.isInsufficientDuration) {
                    Spacer(Modifier.height(24.dp))
                    Text(
                        stringResource(
                            R.string.date_picker_error_insufficient,
                        ),
                        color = NDGLTheme.colors.red500,
                        style = NDGLTheme.typography.bodySmMedium,
                    )
                }
                Spacer(Modifier.weight(1f))
                NDGLCTAButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    type = NDGLCTAButtonAttr.Type.PRIMARY,
                    size = NDGLCTAButtonAttr.Size.LARGE,
                    status = if (state.isDateSelected) {
                        NDGLCTAButtonAttr.Status.ACTIVE
                    } else {
                        NDGLCTAButtonAttr.Status.DISABLED
                    },
                    label = stringResource(R.string.date_picker_complete),
                    onClick = clickCompleteButton,
                )
            }
        }

        if (state.showDialog) {
            NDGLModal(
                onDismissRequest = dismissDialog,
                title = stringResource(R.string.date_picker_modal_title),
                body = stringResource(R.string.date_picker_modal_body),
                negativeButtonText = stringResource(R.string.date_picker_modal_negative),
                positiveButtonText = stringResource(R.string.date_picker_modal_positive),
                onPositiveButtonClick = clickTravelButton,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DatePickerScreenPreview() {
    NDGLTheme {
        DatePickerScreen(
            state = DatePickerState(
                tripDays = 3,
            ),
            selectDate = {},
            selectPreviousMonth = {},
            selectNextMonth = {},
            clickCompleteButton = {},
            clickBackButton = {},
            dismissDialog = {},
            clickTravelButton = {},
            innerPadding = PaddingValues(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DatePickerScreenWithDialogPreview() {
    NDGLTheme {
        DatePickerScreen(
            state = DatePickerState(
                currentYear = 2026,
                currentMonth = 4,
                tripDays = 3,
                startDate = LocalDate(2026, 4, 23),
                endDate = LocalDate(2026, 4, 27),
                showDialog = true,
            ),
            selectDate = {},
            selectPreviousMonth = {},
            selectNextMonth = {},
            clickCompleteButton = {},
            clickBackButton = {},
            dismissDialog = {},
            clickTravelButton = {},
            innerPadding = PaddingValues(),
        )
    }
}
