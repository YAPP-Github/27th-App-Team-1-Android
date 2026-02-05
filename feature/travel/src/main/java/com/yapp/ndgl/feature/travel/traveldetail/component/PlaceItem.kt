package com.yapp.ndgl.feature.travel.traveldetail.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.designsystem.NDGLCheckbox
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.core.util.formatString
import com.yapp.ndgl.feature.travel.traveldetail.PlaceType
import com.yapp.ndgl.feature.travel.traveldetail.TravelPlace
import com.yapp.ndgl.feature.travel.traveldetail.getColor
import kotlin.time.Duration.Companion.minutes

@Composable
internal fun PlaceItem(
    place: TravelPlace,
    onClick: () -> Unit,
    onLongClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        PlaceNumber(number = place.sequence, placeType = place.placeType)
        Spacer(Modifier.width(8.dp))
        PlaceCard(place = place, isEditMode = false)
    }
}

@Composable
internal fun EditablePlaceItem(
    modifier: Modifier = Modifier,
    place: TravelPlace,
    checked: Boolean,
    onCheck: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = NDGLTheme.colors.white,
                    shape = RoundedCornerShape(16.dp),
                )
                .padding(horizontal = 0.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            NDGLCheckbox(checked = checked, onClick = onCheck)
            PlaceCard(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        onCheck()
                    },
                place = place,
                isEditMode = true,
            )
            Icon(
                modifier = modifier,
                imageVector = ImageVector.vectorResource(R.drawable.ic_24_menu),
                contentDescription = null,
                tint = NDGLTheme.colors.black400,
            )
        }
    }
}

@Composable
private fun PlaceNumber(
    number: Int,
    placeType: PlaceType,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(placeType.getColor()),
    ) {
        Text(
            text = number.toString(),
            color = NDGLTheme.colors.white,
            style = NDGLTheme.typography.bodySmSemiBold,
        )
    }
}

@Composable
private fun PlaceCard(
    modifier: Modifier = Modifier,
    place: TravelPlace,
    isEditMode: Boolean,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NDGLTheme.colors.white),
        border = BorderStroke(width = 1.dp, color = NDGLTheme.colors.black50),
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 12.dp, bottom = 16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = ImageVector.vectorResource(place.placeType.iconRes),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${stringResource(place.placeType.labelRes)} • ${
                            stringResource(
                                R.string.estimated_duration_format,
                                place.estimatedDuration.formatString(),
                            )
                        }",
                        color = NDGLTheme.colors.black400,
                        style = NDGLTheme.typography.bodySmMedium,
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = place.name,
                    color = NDGLTheme.colors.black900,
                    style = NDGLTheme.typography.bodyLgSemiBold,
                )

                if (!isEditMode) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = place.description,
                        color = NDGLTheme.colors.black500,
                        style = NDGLTheme.typography.bodyMdMedium,
                    )
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            AsyncImage(
                model = place.thumbnail,
                contentDescription = "Place Thumbnail",
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.LightGray),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

@Preview
@Composable
private fun PlaceItemPreview() {
    NDGLTheme {
        PlaceItem(
            place = TravelPlace(
                id = 1,
                day = 1,
                sequence = 1,
                estimatedDuration = 60.minutes,
                googlePlaceId = "",
                thumbnail = "",
                latitude = 35.6585805,
                longitude = 139.7454329,
                name = "도쿄 타워",
                regularOpeningHours = "09:00~23:00",
                googleMapsUri = "",
                description = "장소에 대한 설명 추가",
                placeType = PlaceType.ATTRACTION,
            ),
            onClick = {},
            onLongClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditablePlaceItemUncheckedPreview() {
    NDGLTheme {
        EditablePlaceItem(
            place = TravelPlace(
                id = 1,
                day = 1,
                sequence = 1,
                estimatedDuration = 60.minutes,
                googlePlaceId = "",
                thumbnail = "",
                latitude = 35.6585805,
                longitude = 139.7454329,
                name = "도쿄 타워",
                regularOpeningHours = "09:00~23:00",
                googleMapsUri = "",
                description = "장소에 대한 설명 추가",
                placeType = PlaceType.ATTRACTION,
            ),
            checked = false,
            onCheck = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EditablePlaceItemCheckedPreview() {
    NDGLTheme {
        EditablePlaceItem(
            place = TravelPlace(
                id = 1,
                day = 1,
                sequence = 1,
                estimatedDuration = 60.minutes,
                googlePlaceId = "",
                thumbnail = "",
                latitude = 35.6585805,
                longitude = 139.7454329,
                name = "도쿄 타워",
                regularOpeningHours = "09:00~23:00",
                googleMapsUri = "",
                description = "장소에 대한 설명 추가",
                placeType = PlaceType.ATTRACTION,
            ),
            checked = true,
            onCheck = {},
        )
    }
}
