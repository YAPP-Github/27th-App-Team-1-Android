plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.home"
}

dependencies {
    implementation(project(":data:travel"))
}
