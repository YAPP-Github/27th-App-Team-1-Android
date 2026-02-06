package com.yapp.ndgl.core.ui.util

import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import timber.log.Timber

fun Context.launchBrowser(url: String) {
    try {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    } catch (e: Exception) {
        Timber.e("Failed to launch browser: $url")
    }
}
