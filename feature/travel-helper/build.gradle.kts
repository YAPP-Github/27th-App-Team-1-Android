plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.travelHelper"
}

dependencies {
    implementation(project(":data:travel"))
}
