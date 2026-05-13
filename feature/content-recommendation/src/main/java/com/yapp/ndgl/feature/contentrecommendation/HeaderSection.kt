package com.yapp.ndgl.feature.contentrecommendation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.theme.NDGLTheme

@Composable
internal fun HeaderSection() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        Text(
            text = stringResource(R.string.content_recommendation_title),
            style = NDGLTheme.typography.titleMdSemiBold,
            color = NDGLTheme.colors.black700,
        )
        Text(
            text = stringResource(R.string.content_recommendation_subtitle),
            style = NDGLTheme.typography.bodyLgRegular,
            color = NDGLTheme.colors.black400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HeaderSectionPreview() {
    NDGLTheme {
        HeaderSection()
    }
}
