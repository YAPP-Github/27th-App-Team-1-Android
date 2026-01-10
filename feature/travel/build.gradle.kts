plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.travel"
}

dependencies {
    implementation(project(":data:travel"))
}
