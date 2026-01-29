package com.yapp.ndgl.core.util

import kotlin.time.Duration

fun Duration.formatString(): String {
    return toComponents { hours, minutes, _, _ ->
        when {
            hours > 0 && minutes > 0 -> "${hours}시간 ${minutes}분"
            hours > 0 -> "${hours}시간"
            else -> "${inWholeMinutes}분"
        }
    }
}
