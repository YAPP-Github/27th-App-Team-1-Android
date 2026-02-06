plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.travel"
}

dependencies {
    implementation(project(":data:travel"))
    implementation(libs.maps.compose)
    implementation(libs.play.services.maps)
    implementation(libs.coil.compose)
    implementation(libs.kotlinx.datetime)
}
