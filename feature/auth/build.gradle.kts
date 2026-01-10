plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.auth"
}

dependencies {
    implementation(project(":data:auth"))
}
