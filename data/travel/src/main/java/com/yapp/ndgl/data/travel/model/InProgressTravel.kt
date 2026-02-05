package com.yapp.ndgl.data.travel.model

import java.time.LocalDate

// FIXME: API 스펙에 맞춰서 모델 재설계
data class InProgressTravel(
    val title: String,
    val dayCount: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val currentPlace: TravelPlace,
) {
    data class TravelPlace(
        val category: String,
        val estimatedTime: String,
        val name: String,
        val thumbnailUrl: String,
    )
}
