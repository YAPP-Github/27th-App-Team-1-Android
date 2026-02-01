package com.yapp.ndgl.feature.travel.followtravel.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.feature.travel.followtravel.Budget

@Composable
fun BudgetBar(
    day: Int,
    budget: Budget,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(NDGLTheme.colors.black50)
            .padding(vertical = 10.dp)
            .padding(start = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_20_piggybank),
            contentDescription = null,
        )
        Text(
            text = stringResource(R.string.budget_bar_format, day, budget.formatString()),
            color = NDGLTheme.colors.black700,
            style = NDGLTheme.typography.bodyMdSemiBold,
        )
    }
}

@Preview
@Composable
private fun BudgetBarPreview() {
    NDGLTheme {
        BudgetBar(
            day = 1,
            budget = Budget(300000),
        )
    }
}
