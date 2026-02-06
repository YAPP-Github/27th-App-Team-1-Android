package com.yapp.ndgl.feature.home.main

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.util.FlagEmojiUtil.toFlagEmoji
import com.yapp.ndgl.data.travel.model.TravelSummary
import com.yapp.ndgl.feature.home.R
import com.yapp.ndgl.core.ui.R as CoreR

@Composable
internal fun RecommendedContentSection(
    userName: String,
    contents: List<TravelSummary>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        Text(
            text = stringResource(R.string.home_recommended_content_section_title, userName),
            style = NDGLTheme.typography.subtitleLgSemiBold,
            color = NDGLTheme.colors.black900,
            modifier = Modifier.padding(horizontal = 24.dp),
        )

        val lazyListState = rememberLazyListState()
        LazyRow(
            state = lazyListState,
            contentPadding = PaddingValues(horizontal = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            flingBehavior = rememberSnapFlingBehavior(lazyListState = lazyListState),
        ) {
            items(
                items = contents,
                key = { it.travelId },
            ) { travel ->
                RecommendedContentCard(travel = travel)
            }
        }
    }
}

@Composable
private fun RecommendedContentCard(
    travel: TravelSummary,
) {
    Column(
        modifier = Modifier
            .width(240.dp)
            .clip(RoundedCornerShape(8.dp)),
    ) {
        AsyncImage(
            model = travel.youtube.thumbnail,
            contentDescription = travel.youtube.title,
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            contentScale = ContentScale.Crop,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NDGLTheme.colors.white)
                .padding(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            CountryChip(
                country = travel.country,
                city = travel.city,
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = travel.youtube.title,
                    style = NDGLTheme.typography.bodyLgSemiBold,
                    color = NDGLTheme.colors.black700,
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = ImageVector.vectorResource(CoreR.drawable.ic_20_video),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp),
                        tint = NDGLTheme.colors.black400,
                    )
                    Text(
                        text = travel.youtube.youtuber,
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.black400,
                    )
                    Text(
                        text = stringResource(R.string.home_common_dot_separator),
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.black400,
                    )
                    Text(
                        text = stringResource(R.string.home_popular_travel_nights_days, travel.nights, travel.days),
                        style = NDGLTheme.typography.bodyMdMedium,
                        color = NDGLTheme.colors.black400,
                    )
                }
            }
        }
    }
}

@Composable
private fun CountryChip(
    country: String,
    city: String,
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color = NDGLTheme.colors.green50)
            .border(
                width = 1.dp,
                color = NDGLTheme.colors.green500,
                shape = RoundedCornerShape(4.dp),
            )
            .padding(horizontal = 10.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = country.toFlagEmoji(),
            style = NDGLTheme.typography.bodySmSemiBold,
        )
        Text(
            text = city,
            style = NDGLTheme.typography.bodySmSemiBold,
            color = NDGLTheme.colors.green500,
        )
    }
}
