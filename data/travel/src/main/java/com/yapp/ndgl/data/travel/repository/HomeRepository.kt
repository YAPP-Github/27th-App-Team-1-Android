package com.yapp.ndgl.data.travel.repository

import com.yapp.ndgl.data.travel.model.InProgressTravel
import com.yapp.ndgl.data.travel.model.TravelSummary
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random

// FIXME: Sample data 제거 및 API 호출
class HomeRepository @Inject constructor() {
    suspend fun getMyTravel(): InProgressTravel {
        return InProgressTravel(
            title = "인도 여행",
            dayCount = 1,
            startDate = LocalDate.of(2024, 12, 23),
            endDate = LocalDate.of(2024, 12, 26),
            currentPlace = InProgressTravel.TravelPlace(
                category = "교통수단",
                estimatedTime = "1시간 체류 예상",
                name = "인도 국제 공항",
                thumbnailUrl = randomImageUrl(),
            ),
        )
    }

    suspend fun getPopularTravels(): List<TravelSummary> {
        return listOf(
            TravelSummary(
                travelId = "Popular1",
                country = "FR",
                city = "파리",
                nights = 7,
                days = 9,
                youtube = TravelSummary.YoutubeInfo(
                    title = "곽준빈의 신혼여행",
                    youtuber = "곽준빈",
                    thumbnail = randomImageUrl(),
                ),
            ),
            TravelSummary(
                travelId = "Popular2",
                country = "CH",
                city = "취리히",
                nights = 5,
                days = 6,
                youtube = TravelSummary.YoutubeInfo(
                    title = "스위스 여행",
                    youtuber = "곽준빈",
                    thumbnail = randomImageUrl(),
                ),
            ),
            TravelSummary(
                travelId = "Popular3",
                country = "DK",
                city = "코펜하겐",
                nights = 4,
                days = 6,
                youtube = TravelSummary.YoutubeInfo(
                    title = "충격적인 북유럽 물가",
                    youtuber = "곽준빈",
                    thumbnail = randomImageUrl(),
                ),
            ),
            TravelSummary(
                travelId = "Popular4",
                country = "MX",
                city = "멕시코시티",
                nights = 4,
                days = 6,
                youtube = TravelSummary.YoutubeInfo(
                    title = "샘플 데이터 1",
                    youtuber = "콩콩팡팡",
                    thumbnail = randomImageUrl(),
                ),
            ),
            TravelSummary(
                travelId = "Popular5",
                country = "IN",
                city = "뭄바이",
                nights = 4,
                days = 6,
                youtube = TravelSummary.YoutubeInfo(
                    title = "샘플 데이터 2",
                    youtuber = "콩콩팡팡",
                    thumbnail = randomImageUrl(),
                ),
            ),
            TravelSummary(
                travelId = "Popular6",
                country = "KR",
                city = "서울",
                nights = 4,
                days = 6,
                youtube = TravelSummary.YoutubeInfo(
                    title = "샘플 데이터 3",
                    youtuber = "콩콩팡팡",
                    thumbnail = randomImageUrl(),
                ),
            ),
        )
    }

    suspend fun getRecommendedTravels(): List<TravelSummary> {
        return listOf(
            TravelSummary(
                travelId = "Recommended1",
                country = "IN",
                city = "뭄바이",
                nights = 5,
                days = 6,
                youtube = TravelSummary.YoutubeInfo(
                    title = "생각보다 깨끗한 인도 경험하기",
                    youtuber = "빠니보틀",
                    thumbnail = randomImageUrl(),
                ),
            ),
            TravelSummary(
                travelId = "Recommended2",
                country = "FR",
                city = "파리",
                nights = 5,
                days = 7,
                youtube = TravelSummary.YoutubeInfo(
                    title = "적나라한 파리",
                    youtuber = "빠니보틀",
                    thumbnail = randomImageUrl(),
                ),
            ),
        )
    }

    private fun randomImageUrl(): String {
        val randomId = Random.nextInt(0, 500)
        return "https://picsum.photos/id/$randomId/200/300"
    }
}
