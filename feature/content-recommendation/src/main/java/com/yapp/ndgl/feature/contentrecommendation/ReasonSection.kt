package com.yapp.ndgl.feature.contentrecommendation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.theme.NDGLTheme

@Composable
internal fun ReasonSection(
    reason: String,
    onReasonChange: (String) -> Unit,
) {
    Column(modifier = Modifier.imePadding(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.content_recommendation_reason_label),
            style = NDGLTheme.typography.bodyMdSemiBold,
            color = NDGLTheme.colors.black700,
        )
        BasicTextField(
            value = reason,
            onValueChange = onReasonChange,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(NDGLTheme.colors.black50)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            textStyle = NDGLTheme.typography.bodyMdRegular.copy(color = NDGLTheme.colors.black700),
            decorationBox = { innerTextField ->
                if (reason.isEmpty()) {
                    Text(
                        text = stringResource(R.string.content_recommendation_reason_placeholder),
                        style = NDGLTheme.typography.bodyMdRegular,
                        color = NDGLTheme.colors.black300,
                    )
                }
                innerTextField()
            },
        )
        Text(
            text = stringResource(R.string.content_recommendation_reason_count, reason.length),
            modifier = Modifier.fillMaxWidth(),
            style = NDGLTheme.typography.bodySmRegular,
            color = NDGLTheme.colors.black400,
            textAlign = TextAlign.End,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ReasonSectionPreview() {
    NDGLTheme {
        ReasonSection(
            reason = "경복궁과 북촌이 너무 예뻐요",
            onReasonChange = {},
        )
    }
}
