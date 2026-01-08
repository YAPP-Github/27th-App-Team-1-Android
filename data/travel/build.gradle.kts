plugins {
    id("ndgl.data")
}

android {
    namespace = "com.yapp.ndgl.data.travel"
}

dependencies {
    implementation(project(":data:core"))
}
