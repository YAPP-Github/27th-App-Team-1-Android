plugins {
    id("ndgl.android.library")
    id("org.jetbrains.kotlin.plugin.serialization")
}

android {
    namespace = "com.yapp.ndgl.navigation"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
}
