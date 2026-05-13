import java.util.Properties

plugins {
    id("ndgl.feature")
}

android {
    namespace = "com.yapp.ndgl.feature.contentrecommendation"

    val localProperties = Properties().apply {
        load(rootProject.file("local.properties").bufferedReader())
    }

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        buildConfigField("String", "NDGL_INQUIRY_URL", "\"${localProperties.getProperty("NDGL_INQUIRY_URL", "")}\"")
    }
}

dependencies {
    implementation(project(":data:travel"))
}
