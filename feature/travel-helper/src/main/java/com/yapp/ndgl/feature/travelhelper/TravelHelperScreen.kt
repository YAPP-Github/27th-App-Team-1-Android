package com.yapp.ndgl.feature.travelhelper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
internal fun TravelHelperRoute(
    innerPadding: PaddingValues = PaddingValues(),
    viewModel: TravelHelperViewModel = hiltViewModel(),
) {
    TravelHelperScreen(innerPadding = innerPadding)
}

@Composable
private fun TravelHelperScreen(
    innerPadding: PaddingValues = PaddingValues(),
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Text(text = "Travel Helper Screen")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TravelHelperScreenPreview() {
    TravelHelperScreen(innerPadding = PaddingValues())
}
