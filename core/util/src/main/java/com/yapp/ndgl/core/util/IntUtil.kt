package com.yapp.ndgl.core.util

import java.text.NumberFormat
import java.util.Locale

fun Int.formatDecimal(): String = NumberFormat.getInstance(Locale.US).format(this)
