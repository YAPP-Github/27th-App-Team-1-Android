plugins {
    id("ndgl.application")
}

android {
    namespace = Configuration.APPLICATION_ID
}

dependencies {
    implementation(project(":navigation"))

    implementation(project(":feature:home"))
    implementation(project(":feature:auth"))
    implementation(project(":feature:travel"))
    implementation(project(":feature:travel-helper"))

    implementation(project(":core:ui"))
}
