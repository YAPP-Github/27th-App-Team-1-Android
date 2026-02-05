package com.yapp.ndgl.core.util

object FlagEmojiUtil {
    private const val FLAG_EMOJI_OFFSET = 127397

    fun String.toFlagEmoji(): String {
        return uppercase().map { character ->
            String(intArrayOf(character.code + FLAG_EMOJI_OFFSET), 0, 1)
        }.joinToString("")
    }
}
