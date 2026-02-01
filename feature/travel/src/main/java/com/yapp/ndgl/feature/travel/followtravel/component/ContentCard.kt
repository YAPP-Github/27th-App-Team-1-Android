package com.yapp.ndgl.feature.travel.followtravel.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.yapp.ndgl.core.ui.R
import com.yapp.ndgl.core.ui.theme.NDGLTheme
import com.yapp.ndgl.feature.travel.followtravel.Budget
import com.yapp.ndgl.feature.travel.followtravel.ContentInfo
import com.yapp.ndgl.feature.travel.followtravel.VideoInfo

@Composable
fun ContentCard(contentInfo: ContentInfo) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f,
        label = "ArrowRotation",
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp),
        colors = CardDefaults.cardColors(containerColor = NDGLTheme.colors.black50),
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(top = 8.dp)
                .padding(bottom = 28.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded },
                verticalAlignment = Alignment.Top,
            ) {
                AsyncImage(
                    model = contentInfo.videoInfo.profileImage,
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(Color.LightGray),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(
                    modifier = Modifier.weight(1f),
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_20_video),
                            contentDescription = null,
                            tint = NDGLTheme.colors.black400,
                        )
                        Text(
                            text = stringResource(
                                R.string.content_card_info_format,
                                contentInfo.videoInfo.name,
                                contentInfo.country,
                                contentInfo.nights,
                                contentInfo.days,
                            ),
                            color = NDGLTheme.colors.black400,
                            style = NDGLTheme.typography.bodyMdMedium,
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.Top,
                    ) {
                        Text(
                            text = contentInfo.videoInfo.title,
                            color = NDGLTheme.colors.black700,
                            style = NDGLTheme.typography.subtitleLgSemiBold,
                            maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f),
                        )
                        Icon(
                            imageVector = ImageVector.vectorResource(R.drawable.ic_28_chevron_down),
                            contentDescription = "Expand",
                            modifier = Modifier
                                .rotate(rotationState)
                                .clickable { isExpanded = !isExpanded },
                        )
                    }
                }
            }
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut(),
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.height(16.dp))
                    AsyncImage(
                        model = contentInfo.videoInfo.thumbnail,
                        contentDescription = "Video Thumbnail",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(NDGLTheme.colors.black200),
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_20_piggybank),
                                contentDescription = null,
                                tint = NDGLTheme.colors.etcBlue,
                                modifier = Modifier.size(20.dp),
                            )
                            Text(
                                text = stringResource(
                                    R.string.content_card_budget_format,
                                    contentInfo.budgetPerPerson.formatString(),
                                ),
                                color = NDGLTheme.colors.etcBlue,
                                style = NDGLTheme.typography.bodyMdSemiBold,
                            )
                        }
                        Spacer(modifier = Modifier.height(10.dp))
                        HorizontalDivider(color = NDGLTheme.colors.black200)
                        Spacer(modifier = Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_20_magic),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp),
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = stringResource(R.string.content_card_video_summary),
                                color = NDGLTheme.colors.black700,
                                style = NDGLTheme.typography.bodyLgSemiBold,
                            )
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = contentInfo.videoInfo.summary,
                            color = NDGLTheme.colors.black500,
                            style = NDGLTheme.typography.bodyMdRegular,
                        )
                    }
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewContent() {
    NDGLTheme {
        ContentCard(
            contentInfo = ContentInfo(
                travelId = "TRAVEL_001",
                country = "태국",
                city = "방콕",
                budgetPerPerson = Budget(1200000),
                nights = 3,
                days = 4,
                videoInfo = VideoInfo(
                    title = "방콕 풀코스, 동남아 안 가본 곽튜브와 함께 【방콕】",
                    name = "빠니보틀",
                    profileImage = "",
                    thumbnail = "",
                    link = "",
                    summary = "빠니보틀은 주말을 이용해 직장인들도 충분히 다녀올 수 있는 '금요일 퇴근 후 방콕 여행'의 가능성을 보여주며, 곽튜브와의 티격태격 케미를 통해 방콕의 매력을 소개합니다",
                ),
            ),
        )
    }
}
