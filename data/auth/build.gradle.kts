plugins {
    id("ndgl.data")
}

android {
    namespace = "com.yapp.ndgl.data.auth"
}

dependencies {
    implementation(project(":data:core"))
}
