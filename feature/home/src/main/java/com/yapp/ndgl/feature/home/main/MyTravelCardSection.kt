package com.yapp.ndgl.feature.home.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.feature.home.R
import com.yapp.ndgl.feature.home.main.HomeState.MyTravel
import com.yapp.ndgl.feature.home.main.HomeState.TravelPlace
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import com.yapp.ndgl.core.ui.R as CoreR

@Composable
internal fun MyTravelCardSection(
    myTravel: MyTravel,
    modifier: Modifier = Modifier,
) {
    when (myTravel) {
        MyTravel.None -> EmptyTravelCard(
            modifier = modifier,
            onCardClick = { /* FIXME: 인기 여행 컨텐츠 전체보기 페이지 이동 */ },
        )

        is MyTravel.Upcoming -> UpcomingTravelCard(
            modifier = modifier,
            travel = myTravel,
            onCardClick = { /* FIXME: 내 여행 페이지 이동 */ },
        )

        is MyTravel.InProgress -> InProgressTravelCard(
            travel = myTravel,
            onCardClick = { /* FIXME: 내 여행 페이지 이동 */ },
            onPlaceClick = { /* FIXME: 장소 상세 보기 페이지 이동 */ },
        )
    }
}

@Composable
private fun EmptyTravelCard(
    modifier: Modifier,
    onCardClick: () -> Unit,
) {
    CardContainer(
        modifier = modifier,
        onCardClick = onCardClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Text(
                    text = stringResource(R.string.home_my_travel_card_empty_title),
                    style = NDGLTheme.typography.bodyLgSemiBold,
                    color = NDGLTheme.colors.black700,
                )
                Text(
                    text = stringResource(R.string.home_my_travel_card_empty_description),
                    style = NDGLTheme.typography.bodyMdMedium,
                    color = NDGLTheme.colors.black400,
                )
            }
            Image(
                painter = painterResource(CoreR.drawable.img_empty_calendar),
                contentDescription = null,
                modifier = Modifier.size(76.dp),
            )
        }
    }
}

@Composable
private fun UpcomingTravelCard(
    modifier: Modifier,
    travel: MyTravel.Upcoming,
    onCardClick: () -> Unit,
) {
    CardContainer(
        modifier = modifier,
        onCardClick = onCardClick,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = travel.imageUrl,
                contentDescription = travel.title,
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    DayTag(dDay = travel.dDay)
                    Text(
                        text = travel.title,
                        style = NDGLTheme.typography.subtitleMdSemiBold,
                        color = NDGLTheme.colors.black700,
                    )
                }
                val dateFormatter = DateTimeFormatter.ofPattern(
                    stringResource(R.string.home_my_travel_card_date_format),
                )
                Text(
                    text = stringResource(
                        R.string.home_my_travel_card_travel_duration,
                        travel.startDate.format(dateFormatter),
                        travel.endDate.format(dateFormatter),
                    ),
                    style = NDGLTheme.typography.bodyMdRegular,
                    color = NDGLTheme.colors.black600,
                )
            }
        }
    }
}

@Composable
private fun DayTag(
    dDay: Int,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = NDGLTheme.colors.black100,
                shape = RoundedCornerShape(999.dp),
            )
            .padding(horizontal = 12.dp, vertical = 4.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = if (dDay <= 0) {
                stringResource(R.string.home_my_travel_card_d_day_minus, dDay)
            } else {
                stringResource(R.string.home_my_travel_card_d_day_plus, dDay)
            },
            style = NDGLTheme.typography.bodyMdMedium,
            color = NDGLTheme.colors.black400,
        )
    }
}

@Composable
private fun InProgressTravelCard(
    travel: MyTravel.InProgress,
    onCardClick: () -> Unit,
    onPlaceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CardContainer(
        modifier = modifier,
        onCardClick = onCardClick,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = stringResource(
                        R.string.home_my_travel_card_in_progress_title,
                        travel.title,
                        travel.dayCount,
                    ),
                    style = NDGLTheme.typography.subtitleMdSemiBold,
                    color = NDGLTheme.colors.black700,
                )
                val dateFormatter = DateTimeFormatter.ofPattern(
                    stringResource(R.string.home_my_travel_card_date_format),
                )
                Text(
                    text = stringResource(
                        R.string.home_my_travel_card_travel_duration,
                        travel.startDate.format(dateFormatter),
                        travel.endDate.format(dateFormatter),
                    ),
                    style = NDGLTheme.typography.bodyMdRegular,
                    color = NDGLTheme.colors.black500,
                )
            }

            PlaceInfoCard(
                place = travel.currentPlace,
                onPlaceClick = onPlaceClick,
            )
        }
    }
}

@Composable
private fun PlaceInfoCard(
    place: TravelPlace,
    onPlaceClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(NDGLTheme.colors.white)
            .clickable(onClick = onPlaceClick)
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(CoreR.drawable.ic_14_car), // FIXME: category icon
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = NDGLTheme.colors.black400,
                )
                Text(
                    text = place.category,
                    color = NDGLTheme.colors.black400,
                    style = NDGLTheme.typography.bodySmMedium,
                )
                Text(
                    text = stringResource(R.string.home_common_dot_separator),
                    color = NDGLTheme.colors.black400,
                    style = NDGLTheme.typography.bodyMdMedium,
                )
                Text(
                    text = place.estimatedTime,
                    color = NDGLTheme.colors.black400,
                    style = NDGLTheme.typography.bodySmMedium,
                )
            }
            Text(
                text = place.name,
                color = NDGLTheme.colors.black900,
                style = NDGLTheme.typography.bodyLgSemiBold,
            )
        }

        AsyncImage(
            model = place.thumbnailUrl,
            contentDescription = place.name,
            modifier = Modifier
                .size(56.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
private fun CardContainer(
    modifier: Modifier,
    onCardClick: () -> Unit,
    content: @Composable BoxScope.() -> Unit,
) {
    Box(
        modifier = modifier
            .padding(horizontal = 24.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(NDGLTheme.colors.black50)
            .clickable(onClick = onCardClick),
        content = content,
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyTravelCardPreview() {
    NDGLTheme {
        MyTravelCardSection(
            modifier = Modifier,
            myTravel = MyTravel.None,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun UpcomingTravelCardPreview() {
    NDGLTheme {
        MyTravelCardSection(
            modifier = Modifier,
            myTravel = MyTravel.Upcoming(
                title = "도쿄 여행",
                dDay = -7,
                startDate = LocalDate.of(2025, 2, 15),
                endDate = LocalDate.of(2025, 2, 20),
                imageUrl = "",
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun InProgressTravelCardPreview() {
    NDGLTheme {
        MyTravelCardSection(
            modifier = Modifier,
            myTravel = MyTravel.InProgress(
                title = "인도 여행",
                dayCount = 3,
                startDate = LocalDate.of(2025, 2, 1),
                endDate = LocalDate.of(2025, 2, 10),
                currentPlace = TravelPlace(
                    category = "교통수단",
                    estimatedTime = "1시간 체류 예상",
                    name = "인도 국제 공항",
                    thumbnailUrl = "",
                ),
            ),
        )
    }
}
